package model;

import streets.Street;

public class Card extends Field {
	
	
	private int cost;
	private int mortgage;
	private model.Player holder;
	private streets.Street street;
	private int[] rentalFee;
	private int house;
	
	
	
	
	public Card(String name, int location, int cost, int mortgage, Street street, int[] rentalFee) {
		super(location,name,true);
		this.cost = cost;
		this.mortgage = mortgage;
		this.street = street;
		this.rentalFee = rentalFee;
		this.holder=null;
		house = 0;
	}

	public int getCost() {
		return cost;
	}

	public int getMortgage() {
		return mortgage;
	}

	public streets.Street getStreet() {
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
