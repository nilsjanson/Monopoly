package gui;

import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import model.Besitzrechtkarte;

public class ConstructionClass extends Thread {

	Pane box;
	Besitzrechtkarte karte;
	boolean build;
	int rotation = 0;

	ConstructionClass(Pane pane, Besitzrechtkarte karte, boolean buy, int rotation) {
		this.box = pane;
		this.karte = karte;
		this.build = buy;
		this.rotation = rotation;
		start();
	}

	public void start() {
		System.out.println("Starte Bau/Abriss auf Strasse: " + karte.getName());
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if (!karte.getName().contains("Bahnhof")) {
					if (build) {
						if (karte.getHausCounter() == 4) {
							box.getChildren().clear();
							ImageView img = new ImageView("/icons/hotel.png");
							img.setFitHeight(box.getHeight() * .25);
							img.setFitWidth(box.getWidth() * .25);
							img.setRotate(rotation);
							box.getChildren().add(img);
						} else if (karte.getHausCounter() == 3) {
							box.getChildren().clear();
							ImageView img = new ImageView("/icons/4house.png");
							img.setFitHeight(box.getHeight() * .25);
							img.setFitWidth(box.getWidth() * .6);
							img.setRotate(rotation);
							box.getChildren().add(img);
						} else if (karte.getHausCounter() == 2) {
							box.getChildren().clear();
							ImageView img = new ImageView("/icons/3house.png");
							img.setFitHeight(box.getHeight() * .25);
							img.setFitWidth(box.getWidth() * .4);
							img.setRotate(rotation);
							box.getChildren().add(img);
						} else if (karte.getHausCounter() == 1) {
							box.getChildren().clear();
							ImageView img = new ImageView("/icons/2house.png");
							img.setFitHeight(box.getHeight() * .25);
							img.setFitWidth(box.getWidth() * .25);
							img.setRotate(rotation);
							box.getChildren().add(img);
						} else if (karte.getHausCounter() == 0) {
							box.getChildren().clear();
							ImageView img = new ImageView("/icons/house.png");
							img.setFitHeight(box.getHeight() * .25);
							img.setFitWidth(box.getWidth() * .25);
							img.setRotate(rotation);
							box.getChildren().add(img);
						}
					} else {
						if (karte.getHausCounter() == 5) {
							box.getChildren().clear();
							ImageView img = new ImageView("/icons/4house.png");
							img.setFitHeight(box.getHeight() * .25);
							img.setFitWidth(box.getWidth() * .25);
							img.setRotate(rotation);
							box.getChildren().add(img);
						} else if (karte.getHausCounter() == 4) {
							box.getChildren().clear();
							ImageView img = new ImageView("/icons/3house.png");
							img.setFitHeight(box.getHeight() * .25);
							img.setFitWidth(box.getWidth() * .25);
							img.setRotate(rotation);
							box.getChildren().add(img);
						} else if (karte.getHausCounter() == 3) {
							box.getChildren().clear();
							ImageView img = new ImageView("/icons/2house.png");
							img.setFitHeight(box.getHeight() * .25);
							img.setFitWidth(box.getWidth() * .25);
							img.setRotate(rotation);
							box.getChildren().add(img);
						} else if (karte.getHausCounter() == 2) {
							box.getChildren().clear();
							ImageView img = new ImageView("/icons/house.png");
							img.setFitHeight(box.getHeight() * .25);
							img.setFitWidth(box.getWidth() * .25);
							img.setRotate(rotation);
							box.getChildren().add(img);
						} else if (karte.getHausCounter() == 1) {
							box.getChildren().clear();
						}
					}
				}
			}
		});
	}

}
