package gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.Semaphore;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class WuerfelStage {
	Stage stage;
	Board board;
	ArrayList<ImageView> views;
	private Semaphore leertaste;
	HBox hbox;
	double min;
	double max;

	public WuerfelStage(Board board, double min, double max) {
		this.min = min;
		this.max = max;
		this.board = board;
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
		hbox.setStyle("-fx-background-color: red;");
		ImageView eins = views.get(views.size() - 1);
		ImageView zwei = views.get(views.size() - 2);
		hbox.getChildren().add(eins);
		hbox.getChildren().add(zwei);
		Scene scene = new Scene(hbox);
		stage = new Stage();
		keyHandler(scene);
		stage.setScene(scene);
		stage.initStyle(StageStyle.UNDECORATED);
		stage.show();
		stage.setWidth(((max - min) / 2) - 5);
		stage.setHeight(min * .15);
		eins.setFitHeight(stage.getHeight());
		zwei.setFitHeight(stage.getHeight());
		eins.setFitWidth(stage.getWidth() / 2);
		zwei.setFitWidth(stage.getWidth() / 2);
		stage.setX(0);
		stage.setY((min / 2) - (stage.getHeight() / 2));
		stage.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> ov, Boolean hidden, Boolean shown) {
				if (shown) {
					stage.toFront();
					board.infoStage.info.toFront();
					board.prime.toFront();
				}
			}
		});
		stage.setOnCloseRequest(e->System.exit(0));
	}

	public void wuerfeln(int x, int y) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				hbox.getChildren().remove(0);
				hbox.getChildren().remove(0);
				ImageView eins = views.get(x - 1);
				ImageView zwei = views.get((y + 6) - 1);
				eins.setFitHeight(min * .15);
				zwei.setFitHeight(min * .15);
				eins.setFitWidth(((max - min) / 2) / 2);
				zwei.setFitWidth(((max - min) / 2) / 2);
				hbox.getChildren().add(eins);
				hbox.getChildren().add(zwei);
			}
		});
	}

	void keyHandler(Scene scene) {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
				case F1:
					board.helpMe();
					break;
				case F2:
					new WuerfelAnimation(views, stage);
					break;
				case SPACE:
					leertaste.release();
					break;
				case ESCAPE:
					new ExitStage(board.prime);
					break;
				default:
					board.prime.toFront();
					break;
				}

			}
		});
	}
	
	public void yourTurn() {
		new WuerfelAnimation(views,stage);
	}

}

class WuerfelAnimation extends Thread {
	ArrayList<ImageView> imgs;
	boolean gewuerfelt;
	Stage stage;

	WuerfelAnimation(ArrayList<ImageView> imgs, Stage stage) {
		this.imgs = imgs;
		this.stage = stage;
		start();
	}

	public void run() {
		wuerfeln();
	}

	void wuerfeln() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				ArrayList<ImageView> img = imgs;
				HBox hbox = new HBox();
				hbox.setStyle("-fx-background-color: red");
				img.remove(img.size()-1);
				img.remove(img.size()-1);
				Collections.shuffle(imgs);
				ImageView img1 = img.get(0);
				img.remove(0);
				img1.setFitHeight(stage.getHeight());
				img1.setFitWidth(stage.getWidth() / 2);
				hbox.getChildren().add(img1);
				Collections.shuffle(imgs);
				ImageView img2 = img.get(0);
				img.remove(0);
				img2.setFitHeight(stage.getHeight());
				img2.setFitWidth(stage.getWidth() / 2);
				hbox.getChildren().add(img2);
				Scene scene = new Scene(hbox);
				stage.setScene(scene);
				try {
					sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
		});
	}

}
