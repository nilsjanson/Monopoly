package gui;

import java.util.Arrays;
import java.util.Collections;

import javafx.scene.image.Image;

public class Wuerfel extends Thread {

	/**
	 * Symbol Array to Represent the dice
	 */
	volatile Image[] views;

	Wuerfel() {
		createWuerfel();
	}

	/**
	 * Initialize dice
	 */
	private void createWuerfel() {
		views = new Image[] { 
				new Image(getClass().getResource("/wuerfel/0.png").toExternalForm()),
				new Image(getClass().getResource("/wuerfel/1.png").toExternalForm()),
				new Image(getClass().getResource("/wuerfel/2.png").toExternalForm()),
				new Image(getClass().getResource("/wuerfel/3.png").toExternalForm()),
				new Image(getClass().getResource("/wuerfel/4.png").toExternalForm()),
				new Image(getClass().getResource("/wuerfel/5.png").toExternalForm()),
				new Image(getClass().getResource("/wuerfel/6.png").toExternalForm()) };
	};
	
	public Image getDice(int x) {
		return views[x+1];
	}

	/**
	 * Method to Spin the dice
	 * 
	 * @return the Symbol Array
	 */
	Image[] spin() {
		Collections.shuffle(Arrays.asList(this.views));
		return this.views;
	}

	/**
	 * Method to get a Random Image to display at the beginning at the game
	 * 
	 * @return
	 */
	Image getStartingSymbol() {
		return views[0];
	}

}
