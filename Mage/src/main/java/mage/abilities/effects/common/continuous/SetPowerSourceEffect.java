
package mage.abilities.effects.common.continuous;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;

/**
 *
 * @author LevelX2
 */
public class SetPowerSourceEffect extends ContinuousEffectImpl {

    private final DynamicValue amount;

    public SetPowerSourceEffect(DynamicValue amount, Duration duration) {
        this(amount, duration, SubLayer.CharacteristicDefining_7a);
    }

    public SetPowerSourceEffect(DynamicValue amount, Duration duration, SubLayer subLayer) {
        super(duration, Layer.PTChangingEffects_7, subLayer, Outcome.BoostCreature);
        this.amount = amount;
        staticText = "{this}'s power is equal to the number of " + amount.getMessage();
    }

    public SetPowerSourceEffect(final SetPowerSourceEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public SetPowerSourceEffect copy() {
        return new SetPowerSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject mageObject = game.getObject(source);
        if (mageObject != null) {
            int value = amount.calculate(game, source, this);
            mageObject.getPower().setValue(value);
            return true;
        }
        return false;
    }

}
