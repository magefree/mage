/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mage.card.arcane;

/**
 * @author stravant@gmail.com
 * Attributes of a card panel outside of the CardView itself that the renderer
 * needs to know in order to render a card.
 */
public class CardPanelAttributes {
    public final int cardWidth;
    public final int cardHeight;
    public final boolean isSelected;
    public final boolean isChoosable;
    
    public CardPanelAttributes(int cardWidth, int cardHeight, boolean isChoosable, boolean isSelected) {
        this.cardWidth = cardWidth;
        this.cardHeight = cardHeight;
        this.isChoosable = isChoosable;
        this.isSelected = isSelected;
    }
}
