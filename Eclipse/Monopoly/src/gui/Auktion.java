package gui;

import java.util.ArrayList;
import java.util.Collections;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Auktion {

	Auktion (Stage prime,String strassenName,String...playerNames) {
		VBox vbox = new VBox();
		vbox.setStyle("-fx-background-color:rgb(" + 192 + "," + 254 + ", " + 213 + ");");
		ImageView icon = new ImageView(getClass().getResource("/besitzkarten/"+strassenName+".jpg").toExternalForm());
		vbox.getChildren().add(icon);
		HBox hbox = new HBox();
		ArrayList<Color> colors = new ArrayList<Color>();
		Color [] carr = new Color [] {Color.RED, Color.BLUE,Color.GREEN,Color.CYAN,Color.PINK,Color.DARKVIOLET,Color.YELLOW,Color.DARKKHAKI,Color.LIME};
		for(Color x: carr) {
			colors.add(x);	
		}
		for (String x: playerNames) {
			Label name = new Label(x);
			Collections.shuffle(colors);
			name.setStyle("-fx-color:rgb("+colors.get(0).getRed()+","+colors.get(0).getGreen()+","+colors.get(0).getBlue()+");");
			name.setStyle("-fx-font-size:20px; -fx-text-fill: black; -fx-padding:0.5em;");
			hbox.getChildren().add(name);
		}
		vbox.getChildren().add(hbox);
		Scene scene = new Scene(vbox);
		prime.setScene(scene);
	}
}
