
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Merlingilb
 */
public class ReturnTargetToOwnersLibraryPermanentEffect extends OneShotEffect {

    private final boolean toTop;

    public ReturnTargetToOwnersLibraryPermanentEffect(boolean top) {
        super(Outcome.Neutral);
        staticText = "Put target card on "+ (top ? "top":"the bottom") + " of its owner's library";
        this.toTop = top;
    }

    public ReturnTargetToOwnersLibraryPermanentEffect(final ReturnTargetToOwnersLibraryPermanentEffect effect) {
        super(effect);
        this.toTop = effect.toTop;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getPermanent(source.getFirstTarget());
        if (card != null) {
            Player owner = game.getPlayer(card.getOwnerId());
            if (owner != null) {
                owner.moveCardToLibraryWithInfo(card, source, game, Zone.STACK, toTop, true);
            }
            return true;
        }
        return false;
    }

    @Override
    public ReturnTargetToOwnersLibraryPermanentEffect copy() {
        return new ReturnTargetToOwnersLibraryPermanentEffect(this);
    }
}
