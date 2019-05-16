package gui;

import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Board {

	private Stage prime;
	private ImageView img = new ImageView(getClass().getResource("/icons/TH-Poly.jpg").toExternalForm());
	private Stage welcome;
	int spieler=0;
	
	public Board (Stage prime) {
		this.prime=prime;
		welcome();
	}
	
	private void createBoard() {
		welcome.close();
		Rectangle2D screen = Screen.getPrimary().getVisualBounds();
		double width=screen.getMaxX();
		double height=screen.getMaxY();
		double max=getMin(width,height);
		VBox vbox= new VBox();
		vbox.setStyle("-fx-background-color: lightgreen");
		img.setFitHeight(max);
		img.setFitWidth(max);
		vbox.getChildren().add(img);
		Scene scene= new Scene(vbox);
		vbox.setAlignment(Pos.CENTER);
		prime.initStyle(StageStyle.UNDECORATED);
		prime.setScene(scene);
		prime.setWidth(max);
		prime.setHeight(max);
		prime.show();
	}
	
	private void welcome() {
		welcome=new Stage();
		VBox vbox = new VBox();
		ImageView logo = new ImageView(getClass().getResource("/icons/TH-Poly-Logo.jpg").toExternalForm());
		vbox.setAlignment(Pos.CENTER);
		vbox.setStyle("-fx-background-color: lightgreen");
		Label welcomel = new Label("");
		HBox hbox = new HBox();
		Button zwei = new Button("2 Spieler");
		Button drei = new Button("3 Spieler");
		Button vier = new Button("4 Spieler");
		butStyle(zwei,drei,vier);
		hbox.getChildren().addAll(zwei,drei,vier);
		hbox.setSpacing(10);
		hbox.setAlignment(Pos.CENTER);
		vbox.getChildren().addAll(logo,welcomel,hbox);
		Scene scene=new Scene(vbox);
		vbox.autosize();
		welcome.setScene(scene);
		welcome.initStyle(StageStyle.UNDECORATED);
		welcome.initModality(Modality.APPLICATION_MODAL);
		welcome.show();
		welcome.centerOnScreen();
		}

	private void butStyle(Button...x) {
		for (Button but: x) {
			but.setStyle("-fx-border-color: black; -fx-background-color: lightgreen; -fx-font-size: 2em;");
			but.setOnAction(e-> createBoard());
		}
	}



double getMin(double x,double y) {
	if (x<y) {
		return x;
	} 
	return y;
}

}