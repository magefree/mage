package mage.components;

import mage.view.CardView;

/**
 * Card info pane for displaying card rules.
 * Supports drawing mana symbols.
 *
 * @author nantuko
 */
public interface CardInfoPane {

    void setCard (final CardView card);
    boolean isCurrentCard (CardView card);
}
