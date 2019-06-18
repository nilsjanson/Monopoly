package main;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import gui.WuerfelStage;
import javafx.stage.Stage;

public class Client {

	gui.Board board;
	Stage stage;
	Socket server;
	DataInputStream in;
	DataOutputStream out;
	int ownPlayerNumber;

	Client(gui.Board board, Stage stage) {
		this.board = board;
		this.stage = stage;
	}

	public void start() {
		try {
			server = new Socket("localhost", 1337);
			in = new DataInputStream(new BufferedInputStream(server.getInputStream()));
			out = new DataOutputStream(new BufferedOutputStream(server.getOutputStream()));
			board.actionSeamphore.acquire();
			out.write(board.spieler);
			out.flush();
			int ownPlayerNumber = in.readInt();
			int x = in.readInt();
			while (x != -1) {
				switch (x) {
				case 2:

				}

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	// Starte den Wuerfelzuh
	public void wuerfel() throws IOException {
		try {
			gui.WuerfelStage w = new WuerfelStage(board);

			int playerNumber = in.readInt(); // erhalte den wuerfelnden SPieler
			if (playerNumber == ownPlayerNumber) { // ist der Client selber am Zug
				System.out.println("Sie sind am Zug druecken sie die Leertaste zum wuerfeln!");
				w.getLeertaste().acquire();
				out.writeBoolean(true);
				out.flush();

			} else { // ist ein anderer CLient am Zug
				System.out.println("Spieler " + playerNumber + " ist am Zug. Warte auf wuerfeln...");
			}

			int wuerfel1 = in.readInt();
			int wuerfel2 = in.readInt();
			w.showWuerfel(stage, wuerfel1, wuerfel2);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}
