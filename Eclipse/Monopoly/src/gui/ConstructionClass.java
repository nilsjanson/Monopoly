package gui;

import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import model.Besitzrechtkarte;

public class ConstructionClass extends Thread {

	Pane box;
	Besitzrechtkarte karte;
	int rotation = 0;

	ConstructionClass(Pane pane, Besitzrechtkarte karte, int rotation) {
		this.box = pane;
		this.karte = karte;
		this.rotation = rotation;
		start();
	}

	public void start() {
		karte.setHausCounter(3);
		System.out.println("Starte Bau/Abriss auf Strasse: " + karte.getName());
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if (!karte.getName().contains("Bahnhof") && !karte.getName().contains("Zollamt") && !karte.getName().contains("Rechenzentrum")) {
					if (karte.getHausCounter() == 5) {
						box.getChildren().clear();
						ImageView img = new ImageView("/icons/hotel.png");
						img.setFitHeight(box.getHeight() * .25);
						img.setFitWidth(box.getWidth() * .25);
						img.setRotate(rotation);
						box.getChildren().add(img);
					} else if (karte.getHausCounter() == 4) {
						box.getChildren().clear();
						ImageView img = new ImageView("/icons/4house.png");
						if (rotation == 90 || rotation == -90) {
							img.setFitHeight(box.getHeight() * .25);
							img.setFitWidth(box.getWidth() * .6);
						} else {
							img.setFitHeight(box.getHeight() * .15);
							img.setFitWidth(box.getWidth() * .9);
						}
						img.setRotate(rotation);
						box.getChildren().add(img);
					} else if (karte.getHausCounter() == 3) {
						box.getChildren().clear();
						ImageView img = new ImageView("/icons/3house.png");
						if (rotation == 90 || rotation == -90) {
							img.setFitHeight(box.getHeight() * .25);
							img.setFitWidth(box.getWidth() * .4);
						} else {
							img.setFitHeight(box.getHeight() * .15);
							img.setFitWidth(box.getWidth() * .7);
						}
						img.setRotate(rotation);
						box.getChildren().add(img);
					} else if (karte.getHausCounter() == 2) {
						box.getChildren().clear();
						ImageView img = new ImageView("/icons/2house.png");
						if (rotation == 90 || rotation == -90) {
							img.setFitHeight(box.getHeight() * .25);
							img.setFitWidth(box.getWidth() * .25);
						} else {
							img.setFitHeight(box.getHeight() * .2);
							img.setFitWidth(box.getWidth() * .5);
						}
						img.setRotate(rotation);
						box.getChildren().add(img);
					} else if (karte.getHausCounter() == 1) {
						box.getChildren().clear();
						ImageView img = new ImageView("/icons/house.png");
						if (rotation == 90 || rotation == -90) {
							img.setFitHeight(box.getHeight() * .2);
							img.setFitWidth(box.getWidth() * .2);
						} else {
							img.setFitHeight(box.getHeight() * .2);
							img.setFitWidth(box.getWidth() * .3);
						}
						img.setRotate(rotation);
						box.getChildren().add(img);
					}
				}
			}
		});
	}

}
