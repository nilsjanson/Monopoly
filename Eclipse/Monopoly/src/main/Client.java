package main;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.Semaphore;

import gui.Board;
import gui.StreetStage;
import gui.WuerfelStage;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class Client extends Thread {

	gui.Board board;
	Stage stage;
	Socket server;
	DataInputStream in;
	DataOutputStream out;
	private int ownPlayerNumber;
	String name;

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
			out.writeInt(board.spieler);
			out.flush();
			int ownPlayerNumber = in.readInt();
			name = board.playerName;
			out.writeUTF(name);
			out.flush();
			board.boardReady.acquire();

			int x = in.readInt();
			System.out.println(x);
			while (x != -1) {
				switch (x) {
				case 2:
					wuerfel(ownPlayerNumber);
					break;

				case 3:
					int player = in.readInt();
					int money = in.readInt();
					changeMoney(player, money);
					break;

				case 4:
					StreetStage s;
					int actualPlayer = in.readInt();
					System.out.println("auf feld gekommen");
					if (in.readBoolean()) { // ist ein Grundstueck
						String streetName = in.readUTF();
						System.out.println("hahahahaha");
						if (in.readBoolean()) { // hat keinen Besitzer
							System.out.println("zweite if");
							if (in.readBoolean()) {
								if (actualPlayer == ownPlayerNumber) {
									System.out.println("Da musste was gehen");
									
									showStreetStage(board, streetName, actualPlayer, true, true, false,true);
								}
								else {
									System.out.println("sp en drekc");
									showStreetStage(board, streetName, actualPlayer, true, true, false,false);
								}
									
							} else {
								if (actualPlayer == ownPlayerNumber) {
									showStreetStage(board, streetName, actualPlayer, false, true, false,true);
								}
								else {
									System.out.println("sp en drekc");
									showStreetStage(board, streetName, actualPlayer, false, true, false,false);
								}
							}
						} else { // Besitzer existiert
							int owner = in.readInt();
							if(owner == ownPlayerNumber ) {
								showStreetStage(board, streetName, actualPlayer, false, true, false,false);
							}else {
								showStreetStage(board, streetName, actualPlayer, false, false, false,false);
							}

						}

					} else { // ist kein Grundstueck
						 int value = in.readInt();
						 switch(value) {
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
				}
				x = in.readInt();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
	
	private void showStreetStage(Board board, String name, int playerNumber,boolean kaufbar, boolean versteigerbar, boolean hausKaufbar,boolean aktionErforderlich) {
		Platform.runLater(new Runnable() {
            @Override public void run() {
            	StreetStage s = new StreetStage(board, name, playerNumber, kaufbar, versteigerbar, hausKaufbar,aktionErforderlich);
            }
        });
		
	}

	// Starte den Wuerfelzuh

	public void changeMoney(int playerNumber, int money) {
		System.out.println("Spieler " + playerNumber + " hat jetzt " + money + "�");
	}

	public void wuerfel(int ownPlayerNumber) throws IOException {

		try {
			board.actionSeamphore = new Semaphore(0);
			System.out.println("Wuerfeln starten");
			int playerNumber = in.readInt(); // erhalte den wuerfelnden SPieler
			System.out.println(playerNumber + " ist am Zug");
			if (playerNumber == ownPlayerNumber) { // ist der Client selber am Zug
				board.wuerfelStage.getLeertaste().acquire(board.wuerfelStage.getLeertaste().availablePermits());
				System.out.println("Sie sind am Zug druecken sie die Leertaste zum wuerfeln!");
				board.wuerfelStage.getLeertaste().acquire();
				out.writeBoolean(true);
				out.flush();
				System.out.println("gewuerfelt");
			} else { // ist ein anderer CLient am Zug
				System.out.println("Spieler " + playerNumber + " ist am Zug. Warte auf wuerfeln...");
			}
			int wuerfel1 = in.readInt();
			int wuerfel2 = in.readInt();
			System.out.println(wuerfel1);
			System.out.println(wuerfel2);

			board.wuerfelStage.wuerfeln(wuerfel1, wuerfel2);

			for (int i = 1; i <= wuerfel1 + wuerfel2; i++) {
				board.move(board.playerArr[playerNumber]);
				Thread.sleep(800);
			}

			board.wuerfelStage.wuerfeln(wuerfel1, wuerfel2);

			board.move(board.playerArr[playerNumber]);
		} catch (Exception ex) {
			System.out.println("An error occured");
			ex.printStackTrace();
		}
	}

}
