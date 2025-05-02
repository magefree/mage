package mage.game;

import mage.cards.Card;

/**
 * For tests only: put to battlefield with additional settings like tapped
 *
 * @author JayDi85
 */
public class PutToBattlefieldInfo {

    private final Card card;
    private final boolean tapped;

    public PutToBattlefieldInfo(Card card, boolean tapped) {
        this.card = card;
        this.tapped = tapped;
    }

    public Card getCard() {
        return card;
    }

    public boolean isTapped() {
        return tapped;
    }
}
