package gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StreetView {
	StreetView(Stage info, String name) {
		VBox vbox = new VBox();
		ImageView img = new ImageView(getClass().getResource("/besitzkarten/" + name + ".jpg").toExternalForm());
		HBox buttons = new HBox();
		Button back = new Button("Zurueck");
		buttons.setSpacing(10);
		butStyle(back);
		back.setOnAction(e -> info.setScene(info.getScene()));
		vbox.setStyle("-fx-background-color: rgb(" + 53 + "," + 250 + ", " + 49 + ");");
		vbox.getChildren().addAll(img, buttons);
		vbox.setAlignment(Pos.CENTER);
		Scene scene = new Scene(vbox);
	}

	private void butStyle(Button... buttons) {
		for (Button but : buttons) {
			but.setStyle("-fx-border-color: black; -fx-border-color: black; -fx-font-size: 2em;");
		}
	}
}
