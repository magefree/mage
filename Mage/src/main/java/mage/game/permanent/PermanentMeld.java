package mage.game.permanent;

import mage.cards.Card;
import mage.game.Game;

import java.util.UUID;

/**
 * @author emerald000
 */
public class PermanentMeld extends PermanentCard {

    public PermanentMeld(Card card, UUID controllerId, Game game) {
        super(card, controllerId, game);
    }

    private PermanentMeld(final PermanentMeld permanent) {
        super(permanent);
    }

    @Override
    public int getManaValue() {
        // 202.3c
        // The mana value of a melded permanent is calculated as though it had the combined mana cost 
        // of the front faces of each card that represents it. If a permanent is a copy of a melded 
        // permanent (even if that copy is represented by two other meld cards), the mana value of 
        // the copy is 0.
        if (this.isCopy()) {
            return 0;
        } else {
            return this.getCard().getManaValue();
        }
    }

    @Override
    public boolean isTransformable() {
        return false;
    }

    @Override
    public PermanentMeld copy() {
        return new PermanentMeld(this);
    }
}
