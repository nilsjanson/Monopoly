package main;

import gui.Board;
import javafx.application.Application;
import javafx.stage.Stage;
/**
 * Diese Klasse startet die Anwendung.
 * In dieser Klasse befindet sich die Main-Methode.
 *
 */
public class Main extends Application {
	Stage prime;
	Board board;
	
	/**
	 * Startet die Anwendung.
	 * @param args wird nicht beachtet, ist nur vorhanden wegen Standardsignatur der Main-Methode
	 */
	public static void main(String...args) {
		launch();
	}

	@Override
	public void start(Stage prime) throws Exception {
		this.prime=prime;
		this.board =new gui.Board(prime);
		new Client(board,prime);
	}
	
}
