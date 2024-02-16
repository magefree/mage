

package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 * @author Mael
 */
public class ShuffleLibraryTargetEffect extends OneShotEffect {

    public ShuffleLibraryTargetEffect() {
        super(Outcome.Neutral);
        this.staticText = "Target player shuffles their library";
    }

    protected ShuffleLibraryTargetEffect(final ShuffleLibraryTargetEffect effect) {
        super(effect);
    }

    @Override
    public ShuffleLibraryTargetEffect copy() {
        return new ShuffleLibraryTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
            player.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }
}
