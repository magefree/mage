
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
 * @author nantuko
 */
public class LifeTotalCantChangeControllerEffect extends ContinuousEffectImpl {

    public LifeTotalCantChangeControllerEffect(Duration duration) {
        super(duration, Layer.PlayerEffects, SubLayer.NA, Outcome.Benefit);
        staticText = "Your life total can't change. <i>(You can't gain or lose life. You can't pay any amount of life except 0.)</i>";
    }

    protected LifeTotalCantChangeControllerEffect(final LifeTotalCantChangeControllerEffect effect) {
        super(effect);
    }

    @Override
    public LifeTotalCantChangeControllerEffect copy() {
        return new LifeTotalCantChangeControllerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.setLifeTotalCanChange(false);
            return true;
        }
        return true;
    }

}
