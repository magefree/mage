package mage.components;

import mage.view.CardView;

import java.awt.*;

/**
 * GUI: card info pane for displaying card rules.
 * Supports drawing mana symbols.
 *
 * @author nantuko
 */
public interface CardInfoPane {

    void setCard(CardView card, Component container);

}
