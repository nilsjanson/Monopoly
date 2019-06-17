package model;

import java.util.ArrayList;

public class Board {

	private model.Field[] cards;
	private model.Player[] player;
	ArrayList<String> streetNames;

	Board() {

	}
	

	 
	
	public void start() {
		while(true) { //Abbruchkriterum einfügen
			for(int i = 0 ; i<player.length; i++) {
				Player actualPlayer = player[i];
				int augenzahl = wuerfeln();
				System.out.println("Player "+ i +" hat eine " + augenzahl +" gewuerfelt");
				cards[actualPlayer.getPosition()].action(actualPlayer);
			}
			
		}
	}

	public static int wuerfeln() {
		int erg = 7;
		while(erg==7) {
			erg = (int)(Math.random()*6)+1;
		}
		return erg;
	}
	
}
