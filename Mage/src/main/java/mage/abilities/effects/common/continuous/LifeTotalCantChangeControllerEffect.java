
package mage.abilities.effects.common.continuous;

import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.players.Player;

import java.util.Collections;
import java.util.List;

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
    public boolean applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> objects) {
        if (objects.isEmpty()) {
            return false;
        }
        for (MageItem object : objects) {
            if (!(object instanceof Player)) {
                continue;
            }
            ((Player) object).setLifeTotalCanChange(false);
        }
        return true;
    }

    @Override
    public List<MageItem> queryAffectedObjects(Layer layer, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        return controller != null ? Collections.singletonList(controller) : Collections.emptyList();
    }

    @Override
    public LifeTotalCantChangeControllerEffect copy() {
        return new LifeTotalCantChangeControllerEffect(this);
    }
}
