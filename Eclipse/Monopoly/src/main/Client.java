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
import gui.EmailStage;
import gui.StreetStage;
import javafx.application.Platform;
import javafx.stage.Stage;
import model.Besitzrechtkarte;
import model.Player;

/**
 * Controller fuer einen Benutzer zwischen Server und grafischer Oberflaeche.
 */
public class Client extends Thread {

	/** Referenz auf das Gui-Board. */
	private gui.Board board;
	/** Stage fuer GUI. */
	private Stage stage;
	/** Socket fuer Serververbindung. */
	private Socket server;
	/** Inputstream der Serververbindung. */
	private DataInputStream in;
	/** Output der Serververbindung. */
	private DataOutputStream out;
	/** Eigene Spielernummer. */
	private int ownPlayerNumber;
	/** ausgewaehlte Anzahl der Spieler. */
	private int anzahlSpieler;
	/** Name des eigenen Spielers. */
	private String name;
	/** Liste aller Spieler im Spiel. */
	private Player[] playerList;

	/**
	 * Erzeugen eines neuen Clients.
	 * @param b GUI Board
	 * @param s GUI Stage
	 */
	Client(final gui.Board b, final Stage s) {
		this.board = b;
		this.stage = s;
		start();
	}

	/** Threadmethode des Clients. */
	public final void run() {
		try {
			board.actionSeamphore.acquire();
			anzahlSpieler = board.spieler;
			server = new Socket(board.getIp(),
					Integer.parseInt(board.getPort()));
			in = new DataInputStream(new BufferedInputStream(
					server.getInputStream()));
			out = new DataOutputStream(new BufferedOutputStream(
					server.getOutputStream()));
			out.writeInt(anzahlSpieler);
			out.flush();
			ownPlayerNumber = in.readInt();
			name = board.playerName;
			out.writeUTF(name);
			out.flush();
			board.boardReady.acquire();
			board.ownPlayerNumber = ownPlayerNumber;
			playerList = new Player[anzahlSpieler];
			for (int i = 0; i < anzahlSpieler; i++) {
				String playerName = in.readUTF();
				int playerID = in.readInt();
				playerList[i] = new Player(playerName,
						playerID);
				System.out.println(playerList[i].getName()
						+ "(" + playerList[i].getID()
						+ ") has joined");
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
						System.out.println("Ich bin dran!");
						board.aktionZugGemachtSem = new Semaphore(0);
	
						board.yourTurn = true;
						System.out.println("Ich bin dran!");
						board.aktionZugGemachtSem.acquire();
						int aktion = board.actionQueue.remove(0);
						if (aktion != 2) {
							out.writeInt(aktion);
							System.out.println("Schicke " + aktion);
							out.flush();
							int position = board.actionQueue.remove(0); // Position �bermitteln
							out.writeInt(position);
							out.flush();
						} else {
							out.writeInt(2);
							out.flush();
						}
						board.yourTurn = false;
					}
					break;
				case 2: // wuerfeln
					wuerfel();
					break;
				case 3: // geld updaten
					int player = in.readInt();
					int money = in.readInt();
					changeMoney(player, money);
					break;
				case 4: // Feld checken
					checkFeld();
					break;
				case 5: // Strasse kaufen
					System.out.println(in.readUTF());
					break;
				case 6: // Auktion starten
					String sName = in.readUTF();
					showAuctionStage(board, sName);
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
				case 7: // Grundstueckbesitzer wechseln
					String gName = in.readUTF();
					int oldOwner = in.readInt();
					int neuerOwner = in.readInt();
					Besitzrechtkarte bKarte = Besitzrechtkarte.findByName(gName);
					if (oldOwner != -1) {
						System.out.println(
								"Spieler " + oldOwner + " wird das Besitzrecht der Karte " + gName + " entzogen");
						board.infoStage.changeColor(bKarte);
					}
					bKarte.setOwner(playerList[neuerOwner]);
					System.out.println(
							"Spieler " + neuerOwner + " wird das Besitzrecht der Karte " + gName + " anerkannt");
					board.infoStage.changeColor(bKarte);
					if (board.streetStageOpen != null) {
						board.streetStageOpen.close();
					}
					break;

				case 8: // bewege dich auf dem Feld
					int playerID = in.readInt();
					int position = in.readInt();
					int dest = in.readInt();
					while (position != dest) {
						board.move(board.playerArr[playerID]);
						Thread.sleep(800);
						position++;
						position = position % 40;
					}
					break;
				case 9: // haus kaufen
					Besitzrechtkarte bK = Besitzrechtkarte.findByName(in.readUTF());
					bK.setHausCounter(in.readInt());
					System.out.println("Auf" + bK.getName() + " wurde ein Haus gebaut");
					break;
				case 10: // haus verkaufen
					Besitzrechtkarte besitz = Besitzrechtkarte.findByPosition(in.readInt());
					besitz.setHausCounter(besitz.getHausCounter() - 1);
					System.out.println("Auf" + besitz.getName() + " wurde ein Haus abgebaut");
					break;
				case 11: // Hypothek aufnehmen
					Besitzrechtkarte besitzRecht = Besitzrechtkarte.findByPosition(in.readInt());
					besitzRecht.setHypothek(!besitzRecht.isHypothek());
					System.out.println("Auf" + besitzRecht.getName() + " wurde Hypothek aufgenommen/Abgenommen");
					board.infoStage.setHypothek(besitzRecht);
					break;
				case 12: //Spieler ist raus
					int playId = in.readInt();
					System.out.println(playerList[playId-1].getName()+" ist raus");
					playerList[playId-1] = null;
					if(playId==ownPlayerNumber) {
						System.out.println("Game over");
						board.youLost();
					} 
					int counter = 0;
					Player winner = null ;
					for(Player p : playerList) {
						if(p!=null) {
							winner = p;
							counter++;
						}
					}
					if(counter==1) {
						System.out.println("Spiel ist vorbei. Spieler: " + winner.getName()+ " hat gewonnen" );
						if(winner.getID()==ownPlayerNumber) {
							board.youWon();
						}
					}
					break;
				default:
					break;
				}
				x = in.readInt();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/** Zeige das Auktionsfenster.
	*@param b Referenz auf das GUI Board
	*@param n Name der zu versteigernden Strasse
	*/
	private void showAuctionStage(final Board b, final String n) {
		try {
			System.out.println("Auktionfenster starten");
			board.auktionStageOpenSemaphore.acquire(board.auktionStageOpenSemaphore.availablePermits());
			System.out.println("name: " + n);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				Auktion auk = new Auktion(board, n, out, "Nils");
			}
		});
	}

	/** Ueberpruefen des Feldes auf dem sich der Spieler befindet.
	 * @throws IOException durch Inputstream
	 */
	private void checkFeld() throws IOException {
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
						showStreetStage(board, streetName, false, true, false, true);
						waitForStreetAction();
					} else {
						showStreetStage(board, streetName, false, true, false, false);
					}
				}
			} else { // Besitzer existiert
				int owner = in.readInt();
				boolean hausKaufbar = in.readBoolean();
				if (owner == actualPlayer && hausKaufbar) {
					if (actualPlayer == ownPlayerNumber) {
						showStreetStage(board, streetName, false, true, false, true);
					} else {
						showStreetStage(board, streetName, false, true, false, false);
					}
				} else {
					System.out.println("zahle miete");
					showStreetStage(board, streetName, false, false, false, false);
				}
			}
		} else { // ist kein Grundstueck
			int v = in.readInt();
			if (v == 77) {
				String text = in.readUTF();
				int acPlayer = in.readInt();
				showEmailStage(text, acPlayer);
			} else if (v == 3) {
				System.out.println("Gehe in das Gefaengnis");
			}
		}
	}

	/**Warte auf ein Input in der StreetStage. */
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
 /**
  * Methode um StreetStage zu zeigen.
  * @param b Referent auf das Board
  * @param n Name der Strasse
  * @param kaufbar ist es kaufbar
  * @param versteigerbar ist es versteigerbar
  * @param hausKaufbar ist ein hauskaufbar
  * @param aktionErforderlich ist eine Aktion erforderlich
  */
	private void showStreetStage(final Board b, final String n, final boolean kaufbar,
			final boolean versteigerbar, final boolean hausKaufbar, final boolean aktionErforderlich) {
		try {
			board.streetStageOpenSemaphore.acquire(board.streetStageOpenSemaphore.availablePermits());

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				Besitzrechtkarte x = Besitzrechtkarte.findByName(n);
				StreetStage s = new StreetStage(b, x, kaufbar, versteigerbar, hausKaufbar, aktionErforderlich);
			}
		});

	}
 /**
  * Zeige eine Email an.
  * @param text Text der abzubilden ist
  * @param playernumber zeige welcher Spieler an der Reihe ist
  */
	private void showEmailStage(final String text, final int playernumber) {

		Platform.runLater(new Runnable() {
			@Override
			public void run() {

				EmailStage s = new EmailStage(board, text, playernumber);
			}
		});

	}

	// Starte den Wuerfelzuh

	/**
	 * Aendert das Geld eines Spielers.
	 * @param playerNumber die Spielernummer des Spielers, dessen Geld geaendert
	 *                     werden soll.
	 * @param money        das neue Geld.
	 */
	public void changeMoney(final int playerNumber, final int money) {
		System.out.println("bin hier drin");
		System.out.println("Spieler " + playerNumber + " hat jetzt " + money + " Euro");
		playerList[playerNumber].setBilanz(money);
		board.infoStage.updateGeld(playerList[playerNumber]);
	}
	/** Methode zum wuerfeln.
	 * @throws IOException wegen Input und Outputstream
	 */
	public final void wuerfel() throws IOException {

		try {

			int playerNumber2 = in.readInt(); // erhalte den wuerfelnden SPieler
			int wuerfel1 = in.readInt();
			int wuerfel2 = in.readInt();

			System.out.println(playerNumber2 + " ist dran");
			board.wuerfelStage.wuerfeln(wuerfel1, wuerfel2);
			if (!in.readBoolean()) {
				for (int i = 1; i <= wuerfel1 + wuerfel2; i++) {
					board.move(board.playerArr[playerNumber2]);
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
