

package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 * @author LevelX2
 */

public class LoseGameTargetPlayerEffect extends OneShotEffect {

    public LoseGameTargetPlayerEffect() {
        super(Outcome.Detriment);
        this.staticText = "that player loses the game";
    }

    protected LoseGameTargetPlayerEffect(final LoseGameTargetPlayerEffect effect) {
        super(effect);
    }

    @Override
    public LoseGameTargetPlayerEffect copy() {
        return new LoseGameTargetPlayerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player target = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (target != null) {
            target.lost(game);
            return true;
        }
        return false;
    }
}
