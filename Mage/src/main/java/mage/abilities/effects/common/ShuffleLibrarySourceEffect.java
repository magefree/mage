

package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author emerald000
 */
public class ShuffleLibrarySourceEffect extends OneShotEffect {

    public ShuffleLibrarySourceEffect() {
        super(Outcome.Neutral);
        this.staticText = "Shuffle your library";
    }

    public ShuffleLibrarySourceEffect(final ShuffleLibrarySourceEffect effect) {
        super(effect);
    }

    @Override
    public ShuffleLibrarySourceEffect copy() {
        return new ShuffleLibrarySourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
			player.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }
}
