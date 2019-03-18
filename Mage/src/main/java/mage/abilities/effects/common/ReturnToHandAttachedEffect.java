

package mage.abilities.effects.common;

import mage.constants.Outcome;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author jeff
 */
public class ReturnToHandAttachedEffect extends OneShotEffect {

    public ReturnToHandAttachedEffect() {
        super(Outcome.ReturnToHand);
        staticText = "return that card to its owner's hand";
    }

    public ReturnToHandAttachedEffect(final ReturnToHandAttachedEffect effect) {
        super(effect);
    }

    @Override
    public ReturnToHandAttachedEffect copy() {
        return new ReturnToHandAttachedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Object object = getValue("attachedTo");
        if (object instanceof Permanent) {
            Card card = game.getCard(((Permanent)object).getId());
            if (card != null) {
                if (card.moveToZone(Zone.HAND, source.getSourceId(), game, false)) {
                    return true;
                }
            }
        }
        return false;
    }

}
