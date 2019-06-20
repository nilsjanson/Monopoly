package gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Player;

public class InfoStage {

	double min;
	double max;
	Scene scene;
	Stage info;
	int playerCount=0;
	
	static Player nils = new Player("Nils", "car.png",0);
	static Player lars = new Player("Lars", "tank.png",0);
	static Player lucas = new Player("Lucas", "dog.png",0);

	public InfoStage(double min, double max) {
		this(min,max,nils, lars, lucas);
	}

	private InfoStage(double min, double max, Player... players) {
		this.min = min;
		this.max = max;
		Label infoSt = new Label("InfoStage");
		infoSt.setStyle("-fx-font-size: 20;");
		info = new Stage();
		HBox names = new HBox();
		HBox money = new HBox();
		for (Player x : players) {
			Label name = new Label(x.getName());
			Label geld = new Label(x.getBilanz() + "€");
			labelStyle(name,geld);
			names.getChildren().add(name);
			money.getChildren().add(geld);
			playerCount++;
		}
		hboxStyle(names,money);
		VBox ivbox = new VBox();
		ivbox.setStyle("-fx-background-color: rgb(" + 192 + "," + 254 + ", " + 213 + ");");
		ivbox.setAlignment(Pos.CENTER);
		ivbox.getChildren().addAll(names,money);
		BorderPane bpane = new BorderPane();
		borderPaneStyle(bpane);
		BorderPane.setAlignment(infoSt, Pos.CENTER);
		bpane.setTop(infoSt);
		bpane.setCenter(ivbox);
		scene = new Scene(bpane);
		info.initStyle(StageStyle.UNDECORATED);
		info.setScene(scene);
		info.setWidth(((max - min) / 2)-5);
		info.setHeight(min * .4);
		info.show();
		info.setX((min+info.getWidth()+10));
		info.setY((min / 2) - (info.getHeight() / 2));
	}
	
	void borderPaneStyle(BorderPane pane) {
		pane.setStyle("-fx-background-color: rgb(" + 53 + "," + 250 + ", " + 49 + ");");
	}
	void hboxStyle(HBox...hboxes) {
		for (HBox x : hboxes) {
			x.setSpacing(40);
			x.setAlignment(Pos.CENTER);
			}
	}
	void labelStyle(Label... labels) {
		for (Label x : labels) {
			x.setStyle("-fx-font-size: 2em;");
		}
	}

}
