package model;

import java.util.ArrayList;
import java.util.Collections;

public class EmailKarte {
	private String text;
	private int geld;
	private static ArrayList<EmailKarte> list  = new ArrayList<EmailKarte>();
	private static EmailKarte a = new EmailKarte("Ihr Bausparvertrag wird faellig. Sie erhalten 200 Euro",+200);
	private static EmailKarte a = new EmailKarte("Ihr Bausparvertrag wird faellig. Sie erhalten 200 Euro",+200);
	
	
	
	public EmailKarte(String text, int geld) {
		this.text = text;
		this.geld = geld;
		list.add(this);
	}
	
	public static EmailKarte getEmail() {
		Collections.shuffle(list);
		return list.get(0);
	}

}
