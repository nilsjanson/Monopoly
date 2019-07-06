package gui;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class StreetView {
	public StreetView(Stage info, String name) {
		Thread thread = new Thread() {
			public void start() {
				createStreetView(info,name);
			}
		};
		thread.start();
		
	}
	
	private void createStreetView(Stage info,String name) {
		BorderPane pane = new BorderPane();
		ImageView img = new ImageView(getClass().getResource("/besitzkarten/" + name + ".jpg").toExternalForm());
		img.setFitHeight(info.getHeight() /2);
		img.setFitWidth(info.getWidth() /2);
		pane.setStyle("-fx-background-color: rgb(" + 192 + "," + 249 + ", " + 213 + ");");
		pane.setCenter(img);
		pane.setPrefWidth(info.getWidth()/2);
		pane.setPrefHeight(info.getHeight()/2);
		Scene old = info.getScene();
		Scene scene = new Scene(pane);
		scene.setOnKeyPressed(new EventHandler<Event>() {
			@Override
			public void handle(Event arg0) {
				info.setScene(old);
			}
		});

		info.setScene(scene);
	}
}
