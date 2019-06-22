package gui;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import model.Besitzrechtkarte;

public class ConstructionClass extends Thread{
	
	Button but;
	Besitzrechtkarte karte;
	boolean build;
	
	ConstructionClass (Button but,Besitzrechtkarte karte,boolean buy) {
		this.but=but;
		this.karte=karte;
		this.build=buy;
		start();
	}
	
	public void run() {
		System.out.println("started Construction Class");
		if (build) {
			if (karte.getHausCounter()==4) {
				System.out.println("start to build hotel on Street: "+but.getText());
				but=new Button(but.getText(),new ImageView("/icons/hotel.png"));
			} else if (karte.getHausCounter()==3) {
				System.out.println("start to build 4th house on Street: "+but.getText());
				but=new Button(but.getText(),new ImageView("/icons/4house.png"));			
			} else if (karte.getHausCounter()==2) {
				System.out.println("start to build 3th house on Street: "+but.getText());
				but=new Button(but.getText(),new ImageView("/icons/3house.png"));
			} else if (karte.getHausCounter()==1) {
				System.out.println("start to build 2nd house on Street: "+but.getText());
				but=new Button(but.getText(),new ImageView("/icons/2house.png"));
			} else if (karte.getHausCounter()==0) {
				System.out.println("start to build 1 house on Street: "+but.getText());
				but=new Button(but.getText(),new ImageView("/icons/house.png"));
			}	
		} else {
			if (karte.getHausCounter()==5) {
				but=new Button(but.getText(),new ImageView("/icons/4house.png"));			
			} else if (karte.getHausCounter()==4) {
				but=new Button(but.getText(),new ImageView("/icons/3house.png"));
			} else if (karte.getHausCounter()==3) {
				but=new Button(but.getText(),new ImageView("/icons/2house.png"));
			} else if (karte.getHausCounter()==2) {
				but=new Button(but.getText(),new ImageView("/icons/house.png"));
			} else if (karte.getHausCounter()==1) {
				but=new Button(but.getText());
			}
		}
		
	}
}
