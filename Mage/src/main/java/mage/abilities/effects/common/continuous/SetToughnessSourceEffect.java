
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
 * @author Backfir3, noxx
 */
public class SetToughnessSourceEffect extends ContinuousEffectImpl {

    private final DynamicValue amount;

    public SetToughnessSourceEffect(DynamicValue amount, Duration duration) {
        this(amount, duration, SubLayer.CharacteristicDefining_7a);
    }

    public SetToughnessSourceEffect(DynamicValue amount, Duration duration, SubLayer subLayer) {
        super(duration, Layer.PTChangingEffects_7, subLayer, Outcome.BoostCreature);
        this.amount = amount;
        staticText = "{this}'s toughness is equal to the number of " + amount.getMessage();
    }

    public SetToughnessSourceEffect(final SetToughnessSourceEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public SetToughnessSourceEffect copy() {
        return new SetToughnessSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject mageObject = game.getObject(source);
        if (mageObject != null) {
            int value = amount.calculate(game, source, this);
            mageObject.getToughness().setValue(value);
            return true;
        } else {
            if (duration == Duration.Custom) {
                discard();
            }
        }
        return false;
    }

}
