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
