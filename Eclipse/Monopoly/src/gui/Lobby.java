package gui;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Lobby extends Application {
	
	ArrayList<String> players;
	
	Lobby(String...player) {
		super();
		players = new ArrayList<String>();
		for (String x: player) {
			players.add(x);
		}
	}

	public void start(Stage arg0) throws Exception {
		Stage prime = new Stage();
		VBox vbox = new VBox();
		vbox.setStyle("-fx-background-color: rgb(" + 192 + "," + 254 + ", " + 213 + ");");
		for (String x: players) {
			Label lobby = new Label(x);
			vbox.getChildren().add(lobby);
		}
		prime.setHeight(200);
		prime.setWidth(200);
		prime.show();
	}

}
