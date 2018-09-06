package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author jmharmon
 */

public class ShuffleGraveyardSourceEffect extends OneShotEffect {

    public ShuffleGraveyardSourceEffect() {
        super(Outcome.Neutral);
        this.staticText = "Shuffle your graveyard";
    }

    public ShuffleGraveyardSourceEffect(final ShuffleGraveyardSourceEffect effect) {
        super(effect);
    }

    @Override
    public ShuffleGraveyardSourceEffect copy() {
        return new ShuffleGraveyardSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.shuffleGraveyard(source, game);
            return true;
        }
        return false;
    }
}
