

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
 * @author LevelX2
 */

public class DontLoseByZeroOrLessLifeEffect extends ContinuousEffectImpl {

    public DontLoseByZeroOrLessLifeEffect(Duration duration) {
        super(duration, Layer.RulesEffects, SubLayer.NA, Outcome.Benefit);
        staticText = "You don't lose the game for having 0 or less life";
    }

    protected DontLoseByZeroOrLessLifeEffect(final DontLoseByZeroOrLessLifeEffect effect) {
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
            ((Player) object).setLoseByZeroOrLessLife(false);
        }
        return true;
    }

    @Override
    public List<MageItem> queryAffectedObjects(Layer layer, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        return controller != null ? Collections.singletonList(controller) : Collections.emptyList();
    }

    @Override
    public DontLoseByZeroOrLessLifeEffect copy() {
        return new DontLoseByZeroOrLessLifeEffect(this);
    }
}