package gui;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Auktion {

	Auktion (Stage prime,String strassenName,String...playerNames) {
		VBox vbox = new VBox();
		ImageView icon = new ImageView(getClass().getResource("/besitzkarten/"+strassenName+".jpg").toExternalForm());
		vbox.getChildren().add(icon);
		HBox hbox = new HBox();
		for (String x: playerNames) {
			Label name = new Label(x);
			name.setStyle("-fx-font-size:20px; -fx-text-fill: black; -fx-background-color:rgb(" + 192 + "," + 254 + ", " + 213 + "); -fx-padding:0.5em;");
			hbox.getChildren().add(name);
		}
		vbox.getChildren().add(hbox);
		Scene scene = new Scene(vbox);
		prime.setScene(scene);
	}
}
