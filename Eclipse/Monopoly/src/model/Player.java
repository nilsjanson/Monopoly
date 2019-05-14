package model;

public class Player {

	
	Player() {
		
	}
	
	
	private void Zug() {
		int augen = wuerfeln() +wuerfeln();
	}
	
	
	private int wuerfeln() {
		int erg = 7;
		while(erg==7) {
			erg = (int)(Math.random()*6)+1;
		}
		return erg;
	}
}
