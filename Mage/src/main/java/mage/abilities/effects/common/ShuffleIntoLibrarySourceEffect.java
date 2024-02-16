

package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 * @author emerald000
 */

public class ShuffleIntoLibrarySourceEffect extends OneShotEffect {

    public ShuffleIntoLibrarySourceEffect() {
        super(Outcome.Neutral);
        staticText = "shuffle it into its owner's library";
    }

    protected ShuffleIntoLibrarySourceEffect(final ShuffleIntoLibrarySourceEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject mageObject = source.getSourceObjectIfItStillExists(game);
        if (mageObject != null) {
            Zone fromZone = game.getState().getZone(mageObject.getId());
            Player owner;
            if (mageObject instanceof Permanent) {
                owner = game.getPlayer(((Permanent) mageObject).getOwnerId());
                if (owner != null) {
                    owner.moveCardToLibraryWithInfo((Permanent) mageObject, source, game, fromZone, true, true);
                    owner.shuffleLibrary(source, game);
                    return true;
                }
            } else if (mageObject instanceof Card) {
                owner = game.getPlayer(((Card) mageObject).getOwnerId());
                if (owner != null) {
                    owner.moveCardToLibraryWithInfo((Card) mageObject, source, game, fromZone, true, true);
                    owner.shuffleLibrary(source, game);
                    return true;
                }
            }


        }
        return false;
    }

    @Override
    public ShuffleIntoLibrarySourceEffect copy() {
        return new ShuffleIntoLibrarySourceEffect(this);
    }
}
