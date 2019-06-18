package animation;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SpinStage extends Application {
	
	public static void main (String...args) {
		launch();
	}
	
	static ParallelTransition spineMe() {
		Rectangle rectParallel = new Rectangle(10, 200, 50, 50);
		rectParallel.setArcHeight(15);
		rectParallel.setArcWidth(15);
		rectParallel.setFill(Color.DARKBLUE);
		rectParallel.setTranslateX(50);
		rectParallel.setTranslateY(75);

		FadeTransition fadeTransition = new FadeTransition(Duration.millis(3000), rectParallel);
		fadeTransition.setFromValue(1.0f);
		fadeTransition.setToValue(0.3f);
		fadeTransition.setCycleCount(2);
		fadeTransition.setAutoReverse(true);
		TranslateTransition translateTransition = new TranslateTransition(Duration.millis(2000), rectParallel);
		translateTransition.setFromX(50);
		translateTransition.setToX(350);
		translateTransition.setCycleCount(2);
		translateTransition.setAutoReverse(true);
		RotateTransition rotateTransition = new RotateTransition(Duration.millis(3000), rectParallel);
		rotateTransition.setByAngle(180f);
		rotateTransition.setCycleCount(4);
		rotateTransition.setAutoReverse(true);
		ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(2000), rectParallel);
		scaleTransition.setToX(2f);
		scaleTransition.setToY(2f);
		scaleTransition.setCycleCount(2);
		scaleTransition.setAutoReverse(true);

		ParallelTransition parallelTransition = new ParallelTransition();
		parallelTransition.getChildren().addAll(fadeTransition, translateTransition, rotateTransition, scaleTransition);
		parallelTransition.setCycleCount(Timeline.INDEFINITE);
		
		return parallelTransition;
	}

	@Override
	public void start(Stage arg0) throws Exception {
		Stage prime = new Stage();
		Pane pane = new Pane();

		ParallelTransition para= spineMe();
		para.play();
		
		prime.setScene(new Scene(pane));
		prime.setHeight(200);
		prime.setWidth(200);
		prime.show();
	}
}
