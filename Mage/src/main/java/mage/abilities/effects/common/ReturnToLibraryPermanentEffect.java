
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 * @author fwannmacher
 */
public class ReturnToLibraryPermanentEffect extends OneShotEffect {

    private final boolean toTop;

    public ReturnToLibraryPermanentEffect(boolean top) {
        super(Outcome.Neutral);
        staticText = "Put {this} on " + (top ? "top" : "the bottom") + " of its owner's library";
        this.toTop = top;
    }

    protected ReturnToLibraryPermanentEffect(final ReturnToLibraryPermanentEffect effect) {
        super(effect);
        this.toTop = effect.toTop;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = game.getPermanent(source.getSourceId());
            if (card != null) {
                controller.moveCardToLibraryWithInfo(card, source, game, Zone.STACK, toTop, true);
            }
            return true;
        }
        return false;
    }

    @Override
    public ReturnToLibraryPermanentEffect copy() {
        return new ReturnToLibraryPermanentEffect(this);
    }
}
