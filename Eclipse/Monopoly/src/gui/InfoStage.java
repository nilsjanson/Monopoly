package gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Semaphore;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import model.Besitzrechtkarte;
import model.Player;

public class InfoStage {

	Board board;
	double min;
	double max;
	Scene scene;
	Stage info;
	int playerCount = 0;
	Label[] geldArr ;

	static Player nils = new Player("Nils", 0);
	static Player lars = new Player("Lars", 0);
	static Player lucas = new Player("Lucas", 0);
	static Player flo = new Player("Florian", 0);

	public InfoStage(Board board, double min, double max) {
		this(board, min, max, nils, lars, lucas,flo);
	}

	 InfoStage(Board board, double min, double max, Player... players) {
		ArrayList<VBox> playerBoxes = new ArrayList<VBox>();
		ArrayList<GridPane> grids = new ArrayList<GridPane>();
		geldArr = new Label[players.length];
		VBox ivbox = new VBox();
		this.board = board;
		this.min = min;
		this.max = max;
		Label infoSt = new Label("InfoStage");
		infoSt.setStyle("-fx-font-size: 20;");
		info = new Stage();

		ivbox.setAlignment(Pos.CENTER);
		int q = 0 ;
		for (Player player : players) {
			VBox playerBox = new VBox();
			playerBoxes.add(playerBox);
			Label name = new Label(player.getName());
			Label geld = new Label(player.getBilanz() + "�");
			geldArr[q] = geld;
			labelStyle(name, geld);
			playerBox.getChildren().add(name);
			playerBox.getChildren().add(geld);
			GridPane grid = new GridPane();
			grids.add(grid);
			grid.setHgap(1);
			grid.setVgap(2);
			playerBox.getChildren().add(grid);
			HashMap<String, Button> streets = createButtons(player);
			player.setStreet(streets);
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
			q++;
		}
		
		
		
		ArrayList<VBox> player = playerBoxes;
		while (playerBoxes.size()!=0) {
			HBox hbox = new HBox();
			while (hbox.getChildren().size()!=2) {
				hbox.getChildren().add(player.get(0));
				player.remove(0);
			}
			ivbox.getChildren().add(hbox);
			hbox = new HBox();
			if (player.size()>=2) {
				while (hbox.getChildren().size()!=2) {
					hbox.getChildren().add(player.get(0));
					player.remove(0);
				}
				ivbox.getChildren().add(hbox);
			} else {
				if (!(player.size()==0)) {
					hbox.getChildren().add(player.get(0));
					player.remove(0);
				}
				ivbox.getChildren().add(hbox);
			}
		}

		ivbox.setStyle("-fx-background-color: rgb(" + 192 + "," + 254 + ", " + 213 + ");");
		ivbox.setSpacing(30);
		ivbox.autosize();
		scene = new Scene(ivbox);
		keyHandler(scene);
		info.initStyle(StageStyle.UNDECORATED);
		info.setScene(scene);
		info.setMaxWidth(((max - min) / 2)-5);
		info.setMaxHeight(min * .8);
		info.show();
		info.setWidth(ivbox.getWidth());
		info.setHeight(ivbox.getHeight());
		info.setX((max-info.getWidth()));
		if (info.getWidth()<(max-min/2)) {
			info.setX(max-min/2+info.getWidth());
		}
		info.setY((min / 2) - (info.getHeight() / 2));
		info.setOnCloseRequest(e->System.exit(0));
		info.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> ov, Boolean hidden, Boolean shown) {
				if (shown) {
					board.wuerfelStage.stage.toFront();
					info.toFront();
					board.prime.toFront();
				}
			}
		});
		board.infoStageSemaphore.release();
	}
	
	public void updateGeld(Player player) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				geldArr[player.getID()].setText(player.getBilanz()+"");
				
			}
		});
	}

	void borderPaneStyle(BorderPane pane) {
		pane.setStyle("-fx-background-color: rgb(" + 53 + "," + 250 + ", " + 49 + ");");
	}

	void labelStyle(Label... labels) {
		for (Label x : labels) {
			x.setStyle("-fx-font-size: 1.5em;");
		}
	}

	void keyHandler(Scene scene) {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
				default:
					board.wuerfelStage.stage.toFront();
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
		street.put("MainzBahnhof", initBut("black", new Button("MainzBahnhof"), info, player));
		street.put("WormsBahnhof", initBut("black", new Button("WormsBahnhof"), info, player));

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
		names.add("MainzBahnhof");
		names.add("WormsBahnhof");
		names.add("Rechenzentrum");
		names.add("Zollamt");
		return names;
	}

	private Button initBut(String color, Button x, Stage prime,Player player) {
		x.setMaxSize(5, 5);
		x.setStyle("-fx-background-color: grey;");
		x.setOnAction(e -> erzeugeStreetStage(x));
		return x;
	}
	
	public void erzeugeStreetStage(Button x) {
	
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				board.streetStageOpen = new StreetStage(board, Besitzrechtkarte.findByName(x.getText()), false , false, false, false);
		}});
		
	
	}

	public void changeColor(Besitzrechtkarte x) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				Player p = x.getOwner();
				Button b = p.getStreetsButton(x.getName());
				if (b.getStyle().equals("-fx-background-color: grey;")) {
					b.setStyle("-fx-background-color:" + x.getColor() + ";");
					
					
				} else {
					b.setStyle("-fx-background-color: grey;");

				}
				
			}
		});
		
	}

	Scene getScene() {
		return scene;
	}
}
