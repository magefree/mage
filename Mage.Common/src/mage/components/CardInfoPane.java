package mage.components;

import mage.cards.Card;
import mage.view.CardView;

/**
 * Card info pane for displaying card rules.
 * Supports drawing mana symbols.
 *
 * @author nantuko
 */
public interface CardInfoPane {
    public void setCard (final CardView card);
    public boolean isCurrentCard (CardView card);
}
