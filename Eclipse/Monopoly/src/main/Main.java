package main;

import java.io.File;

import gui.Board;
import gui.TestBoard;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Diese Klasse startet die Anwendung. In dieser Klasse befindet sich die
 * Main-Methode.
 *
 */
public class Main extends Application {
	Stage prime;
	Board board;

	/**
	 * Startet die Anwendung.
	 * 
	 * @param args
	 *            wird nicht beachtet, ist nur vorhanden wegen Standardsignatur der
	 *            Main-Methode
	 */
	public static void main(String... args) {
		launch();
	}

	@Override
	public void start(Stage prime) throws Exception {
		File dir = new File(".");
		String path = dir.getCanonicalPath();
		if (path.equals("F:\\Studium\\OneDrive - th-bingen.de\\Anlagen\\SENG\\Projekt 2019\\Monopoly\\Monopoly\\Eclipse\\Monopoly") || path.equals("C:\\Java\\SENG\\TH-Poly\\Eclipse\\Monopoly")) { // Ausschliesslich Test der Grafischen Oberflaeche
			TestBoard board = new TestBoard(prime);
		} else {
			System.out.println(dir.getCanonicalPath());
			this.prime = prime;
			this.board = new gui.Board(prime);
			new Client(board, prime);
		}
	}

}
