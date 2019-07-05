package gui;

import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.Reflection;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Lobby {

	VBox vbox;
	Board board;
	Stage lobby;
	
	Lobby(Board board, String... names) {
		this.board=board;
		lobby=new Stage();
		VBox vbox = new VBox();
		vbox.setAlignment(Pos.CENTER);
		Rectangle2D screen = Screen.getPrimary().getVisualBounds();
		double width = screen.getMaxX();
		double height = screen.getMaxY();
		double max = Math.min(width, height);
		vbox.setStyle("-fx-background-color: rgb(" + 192 + "," + 254 + ", " + 213 + ");");
		Label waiting = new Label("Waiting for Clients...");
		waiting.setStyle("-fx-font-size: 20pt;");
		waiting.setCache(true);
		Reflection ref = new Reflection();
		ref.setFraction(0.7f);
		waiting.setEffect(ref);
		vbox.getChildren().add(waiting);
		ImageView thp = new ImageView("/icons/TH-Poly-Logo.jpg");
		vbox.getChildren().add(thp);
		for (String x : names) {
			Label lobby = new Label(x);
			vbox.getChildren().add(lobby);
		}
		lobby.setScene(new Scene(vbox));
		lobby.setHeight(max);
		lobby.setWidth(max);
		lobby.centerOnScreen();
		lobby.initStyle(StageStyle.UNDECORATED);
		lobby.initModality(Modality.WINDOW_MODAL);
		lobby.show();
	}
	
	void addPlayer(String name) {
		Label lobby = new Label(name);
		vbox.getChildren().add(lobby);
	}

}
