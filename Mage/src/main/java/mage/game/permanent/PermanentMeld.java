package mage.game.permanent;

import java.util.UUID;
import mage.cards.Card;
import mage.game.Game;

/**
 *
 * @author emerald000
 */
public class PermanentMeld extends PermanentCard {

    public PermanentMeld(Card card, UUID controllerId, Game game) {
        super(card, controllerId, game);
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
}
