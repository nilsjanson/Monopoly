package main;

import gui.Board;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	Stage prime;
	Board board;
	
	public static void main(String...args) {

		launch();
	}

	@Override
	public void start(Stage prime) throws Exception {
		this.prime=prime;
		this.board =new gui.Board(prime);
	}
	
}
