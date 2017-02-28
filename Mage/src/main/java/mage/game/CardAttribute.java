/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.game;

import mage.ObjectColor;
import mage.cards.Card;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class saves changed attributes of cards (e.g. in graveyard, exile or player hands or libraries).
 * 
 * @author LevelX2
 */
public class CardAttribute  implements Serializable {
    
    protected ObjectColor color;
    protected List<String> subtype;

    public CardAttribute(Card card) {
        color = card.getColor(null).copy();
        subtype = new ArrayList<>(card.getSubtype(null));
    }

    public CardAttribute(CardAttribute cardAttribute) {
        this.color = cardAttribute.color;
        this.subtype = cardAttribute.subtype;
    }
    
    public CardAttribute copy() {
        return new CardAttribute(this);
    }
    
    public ObjectColor getColor() {
       return color;
    }
    
    public List<String> getSubtype() {
        return subtype;
    }
    
}
