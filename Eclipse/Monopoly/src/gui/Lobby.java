package gui;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Lobby {

	Lobby(String...player) {
		Stage prime = new Stage();
		VBox vbox = new VBox();
		vbox.setStyle("-fx-background-color: rgb(" + 192 + "," + 254 + ", " + 213 + ");");
		for (String x: player) {
			Label lobby = new Label(x);
			vbox.getChildren().add(lobby);
		}
		prime.show();
	}

}
