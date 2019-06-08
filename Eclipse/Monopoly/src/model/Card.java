package model;

public class Card extends Field {
	
	
	private int cost;
	private int mortgage;
	private model.Player holder;
	private model.Street street;
	private int[] rentalFee;
	private int house;
	
	
	
	
	public Card(String name, int location, int cost, int mortgage, Street street, int[] rentalFee) {
		super(location,name,true);
		this.cost = cost;
		this.mortgage = mortgage;
		this.street = street;
		street.addCard(this);
		this.rentalFee = rentalFee;
		this.holder=null;
		house = 0;
	}
	
	public void action(Player player) {
		if(holder==null) {
			player.buyCardOption(this);
		}else if(player.equals(holder)) {
			if(street.sameHolder(player)) {
				player.buyHouseOption(this);
			}
		}else {
			if(house == 0 && street.sameHolder(holder)) {
				player.payRental(rentalFee[house]*2);
				holder.earnRental(rentalFee[house]*2);
				System.out.println("Spieler " + player.getID() + "zahlt " + rentalFee[house]*2 + "an Spieler " + holder.getID());
			}else {
				player.payRental(rentalFee[house]);
				holder.earnRental(rentalFee[house]);
				System.out.println("Spieler " + player.getID() + "zahlt " + rentalFee[house] + "an Spieler " + holder.getID());
			}
		
		}
	}

	public int getCost() {
		return cost;
	}

	public int getMortgage() {
		return mortgage;
	}

	public model.Street getStreet() {
		return street;
	}

	public int[] getRentalFee() {
		return rentalFee;
	}

	public model.Player getHolder() {
		return holder;
	}
	public void setHolder(model.Player holder) {
		this.holder = holder;
	}
	
	
	

}
