package main;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.Semaphore;

import gui.Auktion;
import gui.Board;
import gui.StreetStage;
import javafx.application.Platform;
import javafx.stage.Stage;
import model.Besitzrechtkarte;
import model.Player;

public class Client extends Thread {

	gui.Board board;
	Stage stage;
	Socket server;
	DataInputStream in;
	DataOutputStream out;
	private int ownPlayerNumber;
	private int anzahlSpieler;
	String name;
	
	
	Player[] playerList ;

	Client(gui.Board board, Stage stage) {
		this.board = board;
		this.stage = stage;
		start();
	}

	public void run() {
		try {
			server = new Socket("localhost", 1337);
			in = new DataInputStream(new BufferedInputStream(server.getInputStream()));
			out = new DataOutputStream(new BufferedOutputStream(server.getOutputStream()));
			board.actionSeamphore.acquire();
			anzahlSpieler = board.spieler;
			out.writeInt(anzahlSpieler);
			out.flush();
			int ownPlayerNumber = in.readInt();
			name = board.playerName;
			out.writeUTF(name);
			out.flush();
			board.boardReady.acquire();
			board.ownPlayerNumber = ownPlayerNumber;
			playerList = new Player[anzahlSpieler];
			for(int i = 0 ; i < anzahlSpieler ; i++) {
				String playerName  = in.readUTF();
				int playerID =  in.readInt();
				playerList[i] = (new Player(playerName, playerID));
				System.out.println(playerList[i].getName() + "(" + playerList[i].getID()+ ") has joined");
			}
			board.startInfoStage(playerList);
			board.infoStageSemaphore.acquire();
			

			int x = in.readInt();
			while (x != -1) {
				switch (x) {
				
				case 1:
					
					board.actionSeamphore = new Semaphore(0);
					int playerNumber = in.readInt(); // erhalte den Spieler, der dran ist
					System.out.println(playerNumber + " ist am Zug");
					if (playerNumber == ownPlayerNumber) { // ist der Client selber am Zug
						board.aktionZugGemachtSem.acquire(board.aktionZugGemachtSem.availablePermits());
						board.yourTurn = true;
						board.aktionZugGemachtSem.acquire();
						int aktion = board.actionQueue.remove(0);
						
						if(aktion!=0) {
							out.writeInt(aktion);
							out.flush();
							int position = board.actionQueue.remove(0); //Position übermitteln
							out.write(position);
							out.flush();		
							
							
							
						}else {
							out.writeInt(2);
							out.flush();
						}
						
						board.yourTurn= false;
						System.out.println("gewuerfelt");
					}
				
				
				case 2: // wuerfeln
					wuerfel(ownPlayerNumber);
					break;

				case 3: // geld updaten
					int player = in.readInt();
					int money = in.readInt();
					changeMoney(player, money);
					break;

				case 4: // Feld checken
					StreetStage s;
					int actualPlayer = in.readInt();
					if (in.readBoolean()) { // ist ein Grundstueck
						String streetName = in.readUTF();
						if (in.readBoolean()) { // hat keinen Besitzer
							if (in.readBoolean()) {
								if (actualPlayer == ownPlayerNumber) {
									showStreetStage(board, streetName, true, true, false, true);
									waitForStreetAction();

								} else {
									showStreetStage(board, streetName, true, true, false, false);
								}

							} else {
								if (actualPlayer == ownPlayerNumber) {
									showStreetStage(board, streetName,false, true, false, true);
									waitForStreetAction();
								} else {
									showStreetStage(board, streetName, false, true, false, false);
								}
							}
						} else { // Besitzer existiert
							int owner = in.readInt();
							boolean hausKaufbar = in.readBoolean();
							if (owner == actualPlayer && hausKaufbar) {
								if(actualPlayer ==ownPlayerNumber) {
									showStreetStage(board, streetName, false, true, false, true);	
								}else {
									showStreetStage(board, streetName, false, true, false, false);
								}
							} else {
								System.out.println("zahle miete");
								showStreetStage(board, streetName, false, false, false, false);
							}

						}

					} else { // ist kein Grundstueck
						int value = in.readInt();
						switch (value) {
						case 1:
							System.out.println("Ziehe eine Karte von Stapel 1");
							break;
						case 2:
							System.out.println("Ziehe eine Karte von Stapel 2");
							break;
						case 3:
							System.out.println("Gehe in das Gefaengnis");
							break;
						}

					}
					break;

				case 5: // Straï¿½e kaufen
					System.out.println(in.readUTF());
					break;

				case 6: // Auktion starten
					String name = in.readUTF();
					showAuctionStage(board, name);
					board.auktionStageOpenSemaphore.acquire();
					Auktion a = board.auktionStageOpen;
					int actual = in.readInt();
					while (actual != -1) {

						a.neuesGebot(in.readInt());
						if (actual == ownPlayerNumber) {
							a.yourTurn();

						} else {
							a.disableButtons();
						}
						actual = in.readInt();
					}
					a.close(in.readInt());


					break;
				case 7: //Grundstueckbesitzer wechseln
					String gName = in.readUTF();
					int oldOwner = in.readInt();
					int neuerOwner = in.readInt();
					Besitzrechtkarte bKarte = Besitzrechtkarte.findByName(gName);
					if(oldOwner!=-1) {
						System.out.println("Spieler " + oldOwner + " wird das Besitzrecht der Karte " + gName + " entzogen");
						board.infoStage.changeColor(bKarte);
					}
					bKarte.setOwner(playerList[neuerOwner]);
					System.out.println("Spieler " + neuerOwner + " wird das Besitzrecht der Karte " + gName + " anerkannt");
					board.infoStage.changeColor(bKarte);
					if(board.streetStageOpen!=null) {
					board.streetStageOpen.close();
					}
					
					
					break;
					
				case 8:
					int playerID = in.readInt();
					int position = in.readInt();
					int offset ;
					if(position<=10) {
						offset = 10-position;
					}else {
						offset = 40-position +10;
					}
					int counter = position;
					while(counter!=10) {
						board.move(board.playerArr[playerID]);
						Thread.sleep(800);
						counter++;
						counter = counter%40;
					}
					
					break;
				case 9 : //haus kaufen
					Besitzrechtkarte bK = Besitzrechtkarte.findByName(in.readUTF());
					bK.setHausCounter(in.readInt());
					System.out.println("Auf" +bK.getName() + " wurde ein Haus gebaut");
					
					break;
					
				case 10: //haus verkaufen
					Besitzrechtkarte besitz =  Besitzrechtkarte.findByPosition(in.readInt());
					besitz.setHausCounter(besitz.getHausCounter()-1);
					System.out.println("Auf" +besitz.getName() + " wurde ein Haus abgebaut");
					break;
					
				case 11: //Hypothek aufnehmen
					Besitzrechtkarte besitzRecht =  Besitzrechtkarte.findByPosition(in.readInt());
					besitzRecht.setHypothek(!besitzRecht.isHypothek());
					System.out.println("Auf" +besitzRecht.getName() + " wurde ein Haus abgebaut");
					break;
				}
				x = in.readInt();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	private void showAuctionStage(Board board, String name) {
		try {
			System.out.println("Auktionfenster starten");
			board.auktionStageOpenSemaphore.acquire(board.auktionStageOpenSemaphore.availablePermits());
			System.out.println("name: " + name);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				System.out.println(name);
				Auktion auk = new Auktion(board, name,out, "Nils");
			}
		});
	}

	private void waitForStreetAction() {
		try {
			System.out.println("Warte auf Aktion");
			board.streetStageOpenSemaphore.acquire();
			StreetStage streetStage = board.streetStageOpen;
			streetStage.actionSem.acquire();
			int erg = streetStage.action;
			out.writeInt(erg);
			out.flush();

		} catch (Exception ex) {

		}

	}

	private void showStreetStage(Board board, String name, boolean kaufbar, boolean versteigerbar,
			boolean hausKaufbar, boolean aktionErforderlich) {
		try {
			board.streetStageOpenSemaphore.acquire(board.streetStageOpenSemaphore.availablePermits());

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				Besitzrechtkarte x = Besitzrechtkarte.findByName(name);
				StreetStage s = new StreetStage(board,x, kaufbar, versteigerbar, hausKaufbar,
						aktionErforderlich);
			}
		});

	}

	// Starte den Wuerfelzuh

	/**
	 * Aendert das Geld eines Spielers.
	 * @param playerNumber die Spielernummer des Spielers, dessen Geld geaendert werden soll.
	 * @param money das neue Geld.
	 */
	public void changeMoney(int playerNumber, int money) {
		System.out.println("bin hier drin");
		System.out.println("Spieler " + playerNumber + " hat jetzt " + money + " Euro");
		playerList[playerNumber].setBilanz(money);
		board.infoStage.updateGeld(playerList[playerNumber]);
	}

	public void wuerfel(int ownPlayerNumber) throws IOException {

		try {
			
			int playerNumber = in.readInt(); // erhalte den wuerfelnden SPieler
			int wuerfel1 = in.readInt();
			int wuerfel2 = in.readInt();
		

			board.wuerfelStage.wuerfeln(wuerfel1, wuerfel2);
			if(!in.readBoolean()) {
				for (int i = 1; i <= wuerfel1 + wuerfel2; i++) {
					board.move(board.playerArr[playerNumber]);
					Thread.sleep(800);
				}

				board.wuerfelStage.wuerfeln(wuerfel1, wuerfel2);
			}
			

		} catch (Exception ex) {
			System.out.println("An error occured");
			ex.printStackTrace();
		}
	}

}
