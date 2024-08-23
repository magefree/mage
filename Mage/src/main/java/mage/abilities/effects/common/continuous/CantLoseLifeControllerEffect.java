
package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.players.Player;

/**
 * @author Sidorovich77
 */
public class CantLoseLifeControllerEffect extends ContinuousEffectImpl {

    public CantLoseLifeControllerEffect(Duration duration) {
        super(duration, Layer.PlayerEffects, SubLayer.NA, Outcome.Benefit);
        staticText = "Your life total can't change. <i>(You can't lose life. You can't pay any amount of life except 0.)</i>";
    }

    protected CantLoseLifeControllerEffect(final CantLoseLifeControllerEffect effect) {
        super(effect);
    }

    @Override
    public CantLoseLifeControllerEffect copy() {
        return new CantLoseLifeControllerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.setCanLoseLife(false);
            return true;
        }
        return true;
    }

}
