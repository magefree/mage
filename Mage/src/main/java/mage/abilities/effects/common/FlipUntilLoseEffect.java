
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 * @author TheElk801
 */
public class FlipUntilLoseEffect extends OneShotEffect {

    public FlipUntilLoseEffect() {
        super(Outcome.Benefit);
        this.staticText = "flip a coin until you lose a flip";
    }

    protected FlipUntilLoseEffect(final FlipUntilLoseEffect effect) {
        super(effect);
    }

    @Override
    public FlipUntilLoseEffect copy() {
        return new FlipUntilLoseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        while (true) {
            if (!player.flipCoin(source, game, true)) {
                return true;
            }
        }
    }
}
