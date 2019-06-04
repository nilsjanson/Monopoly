package model;

import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Street extends Field {
	Card[] allCards ;
	ImageView img;

	public Street(int location, String name, Color color) {
		super(location, name, true);
		img= new ImageView(getClass().getResource(name).toExternalForm());
	}
	
public boolean sameHolder(Player x) {
	
	for(int i = 0 ; i< allCards.length;i++ ) {
		if(!(x.equals(allCards[i].getHolder()))) {
			return false;
		}
	}
	return true;
}

public void addCard(Card x) {
	if(allCards==null) {
		allCards= new Card[1];
		allCards[0] = x;
	}else {
		Card[] arrHelp = new Card[allCards.length+1];
		for(int i = 0 ; i<allCards.length ; i++) {
			arrHelp[i] = allCards[i];
		}
		arrHelp[allCards.length] = x;
		allCards = arrHelp;
				
	}
}

}
