package gui;

import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Board {

	public Board (Stage prime) {
		ImageView img = new ImageView(getClass().getResource("/icons/TH-Poly.jpg").toExternalForm());
		Rectangle2D screen = Screen.getPrimary().getVisualBounds();
		double width=screen.getMaxX();
		double height=screen.getMaxY();
		double max=getMin(width,height);
		VBox vbox= new VBox();
		vbox.setStyle("-fx-background-color: lightgreen");
		img.setFitHeight(max-50);
		img.setFitWidth(max-50);
		vbox.getChildren().add(img);
		Scene scene= new Scene(vbox);
		vbox.setAlignment(Pos.CENTER);
		prime.setScene(scene);
		prime.setWidth(max);
		prime.setHeight(max);
		prime.show();
	}


double getMin(double x,double y) {
	if (x<y) {
		return x;
	} 
	return y;
}

}