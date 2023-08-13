
package mage.abilities.effects.common.discard;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 * @author LevelX2
 */


public class DiscardHandControllerEffect extends OneShotEffect {

    public DiscardHandControllerEffect() {
        super(Outcome.Discard);
        this.staticText = "discard your hand";
    }

    protected DiscardHandControllerEffect(final DiscardHandControllerEffect effect) {
        super(effect);
    }

    @Override
    public DiscardHandControllerEffect copy() {
        return new DiscardHandControllerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        player.discard(player.getHand().size(), false, false, source, game);
        return true;
    }
}
