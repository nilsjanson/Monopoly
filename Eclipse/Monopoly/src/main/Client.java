package main;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

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
	int ownPlayerNumber;
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

			int x = in.readInt();
			System.out.println(x);
			while (x != -1) {
				switch (x) {
				case 2:
					wuerfel();
					break;
				}

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	// Starte den Wuerfelzuh
	public void wuerfel() throws IOException {

		try {
			System.out.println("Wuerfeln starten");			
			int playerNumber = in.readInt(); // erhalte den wuerfelnden SPieler
			System.out.println(playerNumber+" ist am Zug");
			Thread.sleep(5000);
			if (playerNumber == ownPlayerNumber) { // ist der Client selber am Zug
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
                	
           
			board.move(board.playerArr[playerNumber]);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
