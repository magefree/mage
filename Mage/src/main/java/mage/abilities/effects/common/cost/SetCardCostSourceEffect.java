
package mage.abilities.effects.common.cost;

import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;

/**
 * @author Susucr
 */
public class SetCardCostSourceEffect extends ContinuousEffectImpl {

    private ManaCosts<ManaCost> setCosts;

    public SetCardCostSourceEffect(ManaCosts<ManaCost> setCosts, Duration duration) {
        super(duration, Layer.ColorChangingEffects_5, SubLayer.NA, Outcome.Benefit);
        this.setCosts = setCosts;
    }

    public SetCardCostSourceEffect(final SetCardCostSourceEffect effect) {
        super(effect);
        this.setCosts = effect.setCosts;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject mageObject = game.getObject(source);
        if (mageObject != null) {
            mageObject.setManaCost(this.setCosts);
            return true;
        }

        return false;
    }

    @Override
    public SetCardCostSourceEffect copy() {
        return new SetCardCostSourceEffect(this);
    }
}
