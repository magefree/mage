package org.mage.plugins.card;

import java.io.Serializable;

/**
 * Contains card data and image url.
 * 
 * @author nantuko
 */
public class CardUrl implements Serializable {

	public String name;
    public String set;
    public boolean token = false;
    public Integer collector;
    public String url = "";
    public boolean existsInTheGame = false;

	public CardUrl(String cardName, String cardSet, Integer collectorId, boolean isToken) {
        name = cardName;
        set = cardSet;
        collector = collectorId;
        token = isToken;
    }
    
    @Override
    public boolean equals(Object other) {
    	if (other == null) { return false; }
    	if (other instanceof CardUrl) {
    		return name.equals(((CardUrl) other).name) && set.equals(((CardUrl) other).set)
    			&& collector.equals(((CardUrl) other).collector) && token==((CardUrl)other).token;
    	}
    	return false;
    }
    
    @Override
    public int hashCode() {
    	int hash = 0;
    	hash = name.hashCode();
    	hash = 31*hash + set.hashCode();
    	hash = 31*hash + collector.hashCode();
    	hash = 31*hash + (token ? 1 : 0);
    	return hash;
   	}
    
    public boolean isExistsInTheGame() {
		return existsInTheGame;
	}

	public void setExistsInTheGame(boolean existsInTheGame) {
		this.existsInTheGame = existsInTheGame;
	}
	
    private static final long serialVersionUID = 2L;
}
