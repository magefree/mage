package mage.components;

import mage.cards.Card;

/**
 * Card info pane for displaying card rules.
 * Supports drawing mana symbols.
 *
 * @author nantuko
 */
public interface CardInfoPane {
    public void setCard (final Card card);
    public boolean isCurrentCard (Card card);
}
