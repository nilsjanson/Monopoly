package model;

public class Board {

	private model.Field[] cards;
	private model.Player[] player;
	

	Board() {

	}


	 /*
	public void init() {
		cards = new Field[40];
		cards[0] = new Go(0, "Start");
		 cards[1] = new Street("Kesselhaus", 1, 60, mortgage, street, rentalFee);
		cards[2] = new Card("Asta Kiste",2,); // String name, int location, int cost, int mortgage, Street street, int[] rentalFee
		cards[3] = new Card("Asta Buero",1,60,mortage,street,rentalFee);
		cards[4] = new Card("Semester Beitrag",1,60,mortage,street,rentalFee);
	cards[5] = new Card("Bingen Bahnhof",5,200,mortage,street,rentalFee);
	cards[6] = new Card("Pflangenlabor",6,100,mortage,street,rentalFee);
	cards[7] = new Card("Chance",7,0,mortage,street,rentalFee);
	cards[8] = new Card("Gewaechshaus",8,100,mortage,street,rentalFee);
	cards[9] = new Card("Demonstrationsfeld",9,120,mortage,street,rentalFee);
	cards[9] = new Card("Mathe Vorlesung",10,60,mortage,street,rentalFee);
	cards[10] = new Card("Abgastest",11,140,mortage,street,rentalFee);
	cards[11] = new Card("Rechenzentrum",12,150,mortage,street,rentalFee);
	cards[12] = new Card("Fahrzeuglabor",13,140,mortage,street,rentalFee);
	cards[13] = new Card("Solartankstelle",14,160,mortage,street,rentalFee);
	cards[14] = new Card("Kreuznach Bahnhof",15,200,mortage,street,rentalFee);
	cards[15] = new Card("",16,180,mortage,street,rentalFee);
	cards[16] = new Card("Asta Kiste",17,60,mortage,street,rentalFee);
	cards[17] = new Card("",18,180,mortage,street,rentalFee);
	cards[18] = new Card("",19,200,mortage,street,rentalFee);
	cards[19] = new Card("Bafoeg Amt",20,60,mortage,street,rentalFee);
	cards[20] = new Card("Netzwerklabor",21,220,mortage,street,rentalFee);
	cards[21] = new Card("Chance",22,60,mortage,street,rentalFee);
	cards[21] = new Card("PC-Pool R1-236",23,220,mortage,street,rentalFee);
	cards[22] = new Card("PC-Pool R1-237",24,240,mortage,street,rentalFee);
	cards[23] = new Card("Mainz Bahnhof",25,200,mortage,street,rentalFee);
	cards[24] = new Card("",26,260,mortage,street,rentalFee);
	cards[25] = new Card("",27,260,mortage,street,rentalFee);
	cards[26] = new Card("Bingen Impulse",28,150,mortage,street,rentalFee);
	cards[28] = new Card("",29,280,mortage,street,rentalFee);
	cards[30] = new Card("Ab in die Mathe Vorlesung",30,60,mortage,street,rentalFee);
	cards[31] = new Card("",31,300,mortage,street,rentalFee);
	cards[32] = new Card("",32,300,mortage,street,rentalFee);
	cards[33] = new Card("Asta Kiste",33,60,mortage,street,rentalFee);
	cards[34] = new Card("",34,320,mortage,street,rentalFee);
	cards[35] = new Card("Worms Bahnhof",35,200,mortage,street,rentalFee);
	cards[36] = new Card("Chance",36,60,mortage,street,rentalFee);
	cards[37] = new Card("Mensa",37,350,mortage,street,rentalFee);
	cards[38] = new Card("Schwarz gefahren",38,60,mortage,street,rentalFee);
	cards[39] = new Card("Bibliothek",39,400,mortage,street,rentalFee);

	}
	 
	*/
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
