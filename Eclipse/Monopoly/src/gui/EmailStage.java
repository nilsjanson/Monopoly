package gui;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class EmailStage extends Application {

	Board board;
	String betreff;
	String von;
	String inhalt;

	EmailStage(Board board, String betreff, String von, String inhalt) {
		this.betreff = betreff;
		this.von = von;
		this.inhalt = inhalt;
		this.board=board;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Stage email = new Stage();
		VBox vbox = new VBox();
		vbox.setAlignment(Pos.CENTER);
		Scene scene = new Scene(vbox);
		Label betref = new Label(betreff);
		Label vo = new Label(von);
		Label inhal=new Label(inhalt);
		Rectangle rahmen = new Rectangle();
		rahmen.setStroke(Color.BLACK);
		rahmen.setWidth(email.getWidth() - 10);
		rahmen.setHeight(email.getHeight() - 10);
		vbox.getChildren().addAll(betref, vo, inhal, rahmen);
		email.setScene(scene);
		email.initStyle(StageStyle.UNDECORATED);
		email.initModality(Modality.APPLICATION_MODAL);
		email.show();
		

		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
				case F1:
					board.helpMe();
					break;
				case ESCAPE:
					email.close();
					break;
				default:
					System.out.println(event.getCode() + " erkannt!");
					break;
				}
			}
		});
	}
}
