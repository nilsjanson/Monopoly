package gui;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Player1;
import model.Player2;

public class Board {

	private Stage prime;
	private ImageView img = new ImageView(getClass().getResource("/icons/TH-Poly.jpg").toExternalForm());
	private Stage welcome;
	int spieler=0;
	Pane parent;
	
	public Board (Stage prime) {
		this.prime=prime;
		welcome();
	}
	
	private void createPlayer1(double width, double height) {
		Player1 one = new Player1();
		ImageView icon = one.getIcon();
		height=height/20;
		width=width/20;
		icon.setFitHeight(height);
		icon.setFitWidth(width);
		parent.getChildren().add(icon);
		icon.setX(prime.getWidth()-width);
		icon.setY(prime.getHeight()-height);
	}
	
	private void createPlayer2(double width, double height) {
		Player2 two = new Player2();
		ImageView icon = two.getIcon();
		height=height/20;
		width=width/20;
		icon.setFitHeight(height);
		icon.setFitWidth(width);
		parent.getChildren().add(icon);
		icon.setX(prime.getWidth()-width);
		icon.setY(prime.getHeight()-(2*height));
	}
	
	private void createBoard() {
		welcome.close();
		Rectangle2D screen = Screen.getPrimary().getVisualBounds();
		double width=screen.getMaxX();
		double height=screen.getMaxY();
		double max=getMin(width,height);
		Pane pane = new Pane();
		parent=pane;
		pane.setStyle("-fx-background-color: lightgreen;"
				+ "-fx-background-image: url(\"/icons/TH-Poly.jpg\");"
				+ "    -fx-background-size: "+max+" "+max+";");
		pane.autosize();
//		img.setFitHeight(max);
//		img.setFitWidth(max);
//		vbox.getChildren().add(img);
		Scene scene= new Scene(pane);
		prime.initStyle(StageStyle.UNDECORATED);
		controlBoard(scene);
		prime.setScene(scene);
		prime.setWidth(max);
		prime.setHeight(max);
		prime.show();
		
//		createPlayer1(width,height);
//		createPlayer2(width,height);
		addKreis(width,height);
	}
	
	void addKreis(double width, double height) {
		Circle circle = new Circle(200);
		circle.setStyle("-fx-background-color: red;");
		parent.getChildren().add(circle);
		circle.setCenterY(0);
		
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
		controlWelcome(scene);
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

	private void controlBoard(Scene scene) {
		scene.setOnKeyPressed( new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				switch(event.getCode()) {
				case CONTROL: parent.setRotate(parent.getRotate()-90); break;
				case ESCAPE: System.exit(0); break;
				default: System.out.println(event.getCode()+" erkannt!"); break;
				}
			}
		});
	}
	
	private void controlWelcome(Scene scene) {
		scene.setOnKeyPressed( new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				switch(event.getCode()) {
				case ESCAPE: System.exit(0); break;
				default: System.out.println(event.getCode()+" erkannt!"); break;
				}
			}
		});
	}
	
	
	double getMin(double x,double y) {
	if (x<y) {
		return x;
	} 
	return y;
}

}