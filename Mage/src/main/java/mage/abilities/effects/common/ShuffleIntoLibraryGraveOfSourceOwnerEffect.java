
package mage.abilities.effects.common;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 * @author LevelX2
 */
public class ShuffleIntoLibraryGraveOfSourceOwnerEffect extends OneShotEffect {

    public ShuffleIntoLibraryGraveOfSourceOwnerEffect() {
        super(Outcome.Benefit);
        staticText = "its owner shuffles their graveyard into their library";
    }

    protected ShuffleIntoLibraryGraveOfSourceOwnerEffect(final ShuffleIntoLibraryGraveOfSourceOwnerEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID ownerId = game.getOwnerId(source.getSourceId());
        if (ownerId == null) {
            return false;
        }
        Player owner = game.getPlayer(ownerId);
        if (owner != null) {
            owner.moveCards(owner.getGraveyard(), Zone.LIBRARY, source, game);
            owner.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }

    @Override
    public ShuffleIntoLibraryGraveOfSourceOwnerEffect copy() {
        return new ShuffleIntoLibraryGraveOfSourceOwnerEffect(this);
    }
}
