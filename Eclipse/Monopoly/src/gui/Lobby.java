package gui;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Scene;
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

	public void start(Stage prime) throws Exception {
		Stage plLobby = new Stage();
		VBox vbox = new VBox();
		vbox.setStyle("-fx-background-color: rgb(" + 192 + "," + 254 + ", " + 213 + ");");
		for (String x: players) {
			Label lobby = new Label(x);
			vbox.getChildren().add(lobby);
		}
		plLobby.setScene(new Scene(vbox));
		plLobby.setHeight(200);
		plLobby.setWidth(200);
		plLobby.show();
	}

}
