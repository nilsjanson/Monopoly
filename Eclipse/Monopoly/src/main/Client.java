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
				}

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	// Starte den Wuerfelzuh
	public void wuerfel() throws IOException {

		System.out.println("Wuerfeln starten");
		Platform.runLater(new Runnable() {
			public void run() {
				try {
					gui.WuerfelStage w = new WuerfelStage(board);
					w.start(new Stage());
					int playerNumber = in.readInt(); // erhalte den wuerfelnden SPieler
					if (playerNumber == ownPlayerNumber) { // ist der Client selber am Zug
						System.out.println("Sie sind am Zug druecken sie die Leertaste zum wuerfeln!");
						w.getLeertaste().acquire();
						out.writeBoolean(true);
						out.flush();
						System.out.println("gewuerfelt");

					} else { // ist ein anderer CLient am Zug
						System.out.println("Spieler " + playerNumber + " ist am Zug. Warte auf wuerfeln...");
					}

					int wuerfel1 = in.readInt();
					int wuerfel2 = in.readInt();
					System.out.println(wuerfel2);
					w.showWuerfel(stage, wuerfel1, wuerfel2);
					board.move(board.playerArr[playerNumber]);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

		});

	}
}
