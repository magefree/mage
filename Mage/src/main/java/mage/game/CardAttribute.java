/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.game;

import java.io.Serializable;
import mage.ObjectColor;
import mage.cards.Card;

/**
 * This class saves changed attributes of cards (e.g. in graveyard, exile or player hands or libraries).
 * 
 * @author LevelX2
 */
public class CardAttribute  implements Serializable {
    
    protected ObjectColor color;

    public CardAttribute(Card card) {
        color = card.getColor(null).copy();
    }

    public CardAttribute(CardAttribute cardAttribute) {
        this.color = cardAttribute.color;
    }
    
    public CardAttribute copy() {
        return new CardAttribute(this);
    }
    
    public ObjectColor getColor() {
       return color;
    }
    
}
