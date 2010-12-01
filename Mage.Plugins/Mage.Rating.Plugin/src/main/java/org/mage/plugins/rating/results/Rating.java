package org.mage.plugins.rating.results;

public class Rating {
	public String winnerCardName;
	public String loserCardName;
	
	public Rating(String win, String lose) {
		this.winnerCardName = win;
		this.loserCardName = lose; 
	}
}
