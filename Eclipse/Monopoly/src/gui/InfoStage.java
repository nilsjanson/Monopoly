package gui;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Player;

public class InfoStage {

	Board board;
	double min;
	double max;
	Scene scene;
	Stage info;
	int playerCount = 0;

	static Player nils = new Player("Nils", "car.png", 0);
	static Player lars = new Player("Lars", "tank.png", 0);
	static Player lucas = new Player("Lucas", "dog.png", 0);

	public InfoStage(Board board, double min, double max) {
		this(board, min, max, nils, lars, lucas);
	}

	private InfoStage(Board board, double min, double max, Player... players) {
		this.board = board;
		this.min = min;
		this.max = max;
		Label infoSt = new Label("InfoStage");
		infoSt.setStyle("-fx-font-size: 20;");
		info = new Stage();
		HBox ihbox = new HBox();
		ihbox.setAlignment(Pos.CENTER);
		ihbox.setSpacing(20);
		for (Player player : players) {
			VBox playerBox = new VBox();
			Label name = new Label(player.getName());
			Label geld = new Label(player.getBilanz() + "€");
			labelStyle(name, geld);
			playerBox.getChildren().add(name);
			playerBox.getChildren().add(geld);
			playerCount++;
			ihbox.getChildren().add(playerBox);
			GridPane grid = new GridPane();
			grid.setAlignment(Pos.CENTER);
			grid.setHgap(1);
			grid.setVgap(2);
			playerBox.getChildren().add(grid);
			HashMap<String, Button> streets = createButtons(player);
			ArrayList<String> names = getNames();
			int x = 0;
			int y = 0;
			int counter = 0;
			for (int i = 0; i < player.getButtons().size(); i++) {
				grid.add(streets.get(names.get(i)), x, y);
				x++;
				if (counter < 23) {
					if (x == 3 || counter == 1 || counter == 21) {
						x = 0;
						y += 1;
					}
					counter++;
				} else {
					if (x==4) {
						x=0;
						y++;
					} 					
					
					counter++;
				}

			}
			grid.autosize();
		}
		ihbox.setStyle("-fx-background-color: rgb(" + 192 + "," + 254 + ", " + 213 + ");");
		ihbox.setAlignment(Pos.CENTER);
		BorderPane bpane = new BorderPane();
		borderPaneStyle(bpane);
		BorderPane.setAlignment(infoSt, Pos.CENTER);
		bpane.setTop(infoSt);
		bpane.setCenter(ihbox);
		scene = new Scene(bpane);
		keyHandler(scene);
		info.initStyle(StageStyle.UNDECORATED);
		info.setScene(scene);
		info.setWidth(((max - min) / 2) - 5);
		info.setMaxWidth(((max - min) / 2) - 5);
		info.setHeight(min * .5);
		info.show();
		info.setX((min + info.getWidth() + 10));
		info.setY((min / 2) - (info.getHeight() / 2));
		System.out.println("info:"+info.getWidth());
	}

	void borderPaneStyle(BorderPane pane) {
		pane.setStyle("-fx-background-color: rgb(" + 53 + "," + 250 + ", " + 49 + ");");
	}

	void hboxStyle(HBox... hboxes) {
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

	void keyHandler(Scene scene) {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
				default:
					board.prime.toFront();
					break;
				}

			}
		});
	}

	private HashMap<String, Button> createButtons(Player player) {
		HashMap<String, Button> street = new HashMap<String, Button>();
		street.put("KesselHaus", initBut("purple", new Button("Kesselhaus"), info, player));
		street.put("AstaBuero", initBut("purple", new Button("AstaBuero"), info, player));

		street.put("PflanzenLabor", initBut("lightblue", new Button("PflanzenLabor"), info, player));
		street.put("GewaechsHaus", initBut("lightblue", new Button("GewaechsHaus"), info, player));
		street.put("DemonstrationsFeld", initBut("lightblue", new Button("DemonstrationsFeld"), info, player));

		street.put("MotorenPruefstand", initBut("magenta", new Button("MotorenPruefstand"), info, player));
		street.put("FahrzeugLabor", initBut("magenta", new Button("FahrzeugLabor"), info, player));
		street.put("SolarTankstelle", initBut("magenta", new Button("SolarTankstelle"), info, player));

		street.put("TrvRhenania", initBut("ORANGERED", new Button("TrvRhenania"), info, player));
		street.put("BingerBeasts", initBut("ORANGERED", new Button("BingerBeasts"), info, player));
		street.put("BingenImpulse", initBut("ORANGERED", new Button("BingenImpulse"), info, player));

		street.put("NetzwerkLabor", initBut("red", new Button("NetzwerkLabor"), info, player));
		street.put("PcPool236", initBut("red", new Button("PcPool236"), info, player));
		street.put("PcPool237", initBut("red", new Button("PcPool237"), info, player));

		street.put("StudienBeratung", initBut("yellow", new Button("StudienBeratung"), info, player));
		street.put("StudienSekretariat", initBut("yellow", new Button("StudienSekretariat"), info, player));
		street.put("DekanBuero", initBut("yellow", new Button("DekanBuero"), info, player));

		street.put("RhenoTeutonia", initBut("lime", new Button("RhenoTeutonia"), info, player));
		street.put("Holsatia", initBut("lime", new Button("Holsatia"), info, player));
		street.put("Markomannia", initBut("lime", new Button("Markomannia"), info, player));

		street.put("Mensa", initBut("mediumblue", new Button("Mensa"), info, player));
		street.put("Bibliothek", initBut("mediumblue", new Button("Bibliothek"), info, player));

		street.put("BingenBahnhof", initBut("black", new Button("BingenBahnhof"), info, player));
		street.put("KreuznachBahnhof", initBut("black", new Button("KreuznachBahnhof"), info, player));
		street.put("WormsBahnhof", initBut("black", new Button("WormsBahnhof"), info, player));
		street.put("MainzBahnhof", initBut("black", new Button("MainzBahnhof"), info, player));

		street.put("Rechenzentrum", initBut("snow", new Button("Rechenzentrum"), info, player));
		street.put("Zollamt", initBut("snow", new Button("Zollamt"), info, player));

		ArrayList<Button> buttons = new ArrayList<Button>();

		for (Button button : street.values()) {
			buttons.add(button);
		}
		player.setButtons(buttons);
		return street;
	}

	private ArrayList<String> getNames() {
		ArrayList<String> names = new ArrayList<String>();
		names.add("KesselHaus");
		names.add("AstaBuero");
		names.add("PflanzenLabor");
		names.add("GewaechsHaus");
		names.add("DemonstrationsFeld");
		names.add("MotorenPruefstand");
		names.add("FahrzeugLabor");
		names.add("SolarTankstelle");
		names.add("TrvRhenania");
		names.add("BingerBeasts");
		names.add("BingenImpulse");
		names.add("NetzwerkLabor");
		names.add("PcPool236");
		names.add("PcPool237");
		names.add("StudienBeratung");
		names.add("StudienSekretariat");
		names.add("DekanBuero");
		names.add("RhenoTeutonia");
		names.add("Holsatia");
		names.add("Markomannia");
		names.add("Mensa");
		names.add("Bibliothek");
		names.add("BingenBahnhof");
		names.add("KreuznachBahnhof");
		names.add("WormsBahnhof");
		names.add("MainzBahnhof");
		names.add("Rechenzentrum");
		names.add("Zollamt");
		return names;
	}

	private Button initBut(String color, Button x, Stage prime,Player player) {
		x.prefHeightProperty().bind(prime.heightProperty().divide(28 / 20));
		x.prefWidthProperty().bind(prime.widthProperty().divide(28 / 10));
		x.setStyle("-fx-background-color: grey; -fx-border-color: black;-fx-font-size: 0em; -fx-font-color: transparent;");
		x.setOnAction(e -> changeColor(x, color));
		return x;
	}

	private void changeColor(Button x, String color) {
		if (x.getStyle() == "-fx-background-color: grey") {
			x.setStyle("-fx-background-color:" + color);
		} else {
			x.setStyle("-fx-background-color: grey");
			new StreetView(info,x.getText());
		}
	}

	Scene getScene() {
		return scene;
	}
}
