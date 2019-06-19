package gui;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class WuerfelStage {
	Stage stage;
	Board game;
	ArrayList<ImageView> views ;
	private Semaphore leertaste ;
	HBox hbox;
	double min;
	double max;

	public WuerfelStage(Board board,double min, double max) {
		this.min=min;
		this.max = max;
		game = board;
		try {
			views = new ArrayList<ImageView>();
			views.add(new ImageView(getClass().getResource("/wuerfel/1.png").toExternalForm()));
			views.add(new ImageView(getClass().getResource("/wuerfel/2.png").toExternalForm()));
			views.add(new ImageView(getClass().getResource("/wuerfel/3.png").toExternalForm()));
			views.add(new ImageView(getClass().getResource("/wuerfel/4.png").toExternalForm()));
			views.add(new ImageView(getClass().getResource("/wuerfel/5.png").toExternalForm()));
			views.add(new ImageView(getClass().getResource("/wuerfel/6.png").toExternalForm()));

			views.add(new ImageView(getClass().getResource("/wuerfel/1.png").toExternalForm()));
			views.add(new ImageView(getClass().getResource("/wuerfel/2.png").toExternalForm()));
			views.add(new ImageView(getClass().getResource("/wuerfel/3.png").toExternalForm()));
			views.add(new ImageView(getClass().getResource("/wuerfel/4.png").toExternalForm()));
			views.add(new ImageView(getClass().getResource("/wuerfel/5.png").toExternalForm()));
			views.add(new ImageView(getClass().getResource("/wuerfel/6.png").toExternalForm()));

			views.add(new ImageView(getClass().getResource("/wuerfel/0.png").toExternalForm()));
			views.add(new ImageView(getClass().getResource("/wuerfel/0.png").toExternalForm()));
			leertaste = new Semaphore(0);
			start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Semaphore getLeertaste() {
		return leertaste;
	}


	public void start() {
		hbox = new HBox();
		ImageView eins = views.get(views.size() - 1);
		ImageView zwei = views.get(views.size() - 2);
		hbox.getChildren().add(eins);
		hbox.getChildren().add(zwei);
		Scene scene = new Scene(hbox);
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
				case SPACE:
					leertaste.release();
					break;
				case W:
					leertaste.release();
					break;
				case F1:
					game.helpMe();
					break;
				case ENTER:
					stage.close();
					break;
				case ESCAPE:
				System.exit(0);
				break;
				default:
					break;
				}
			}
		});
		stage = new Stage();
		stage.setScene(scene);
		stage.initStyle(StageStyle.UNDECORATED);
		stage.show();
		stage.setAlwaysOnTop(true);
		eins.setFitHeight(min*.2);
		zwei.setFitHeight(min*.2);
		eins.setFitWidth(((max-min)/2)/2);
		zwei.setFitWidth(((max-min)/2)/2);
		stage.setWidth((max-min)/2);
		stage.setHeight(min*.2);
		stage.setX(0);
		stage.setY((min / 2)-(stage.getHeight()/2));
	}

	public void wuerfeln(int x, int y) {
		hbox.getChildren().remove(0);
		hbox.getChildren().remove(0);
		hbox.getChildren().add(views.get(x - 1));
		hbox.getChildren().add(views.get((y + 6) - 1));

	}
}
