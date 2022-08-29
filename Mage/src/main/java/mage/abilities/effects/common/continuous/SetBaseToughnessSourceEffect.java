
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
 *  RENAME
 * @author Backfir3, noxx
 */
public class SetBaseToughnessSourceEffect extends ContinuousEffectImpl {

    private final DynamicValue amount;

    public SetBaseToughnessSourceEffect(DynamicValue amount, Duration duration) {
        this(amount, duration, SubLayer.CharacteristicDefining_7a);
    }

    public SetBaseToughnessSourceEffect(DynamicValue amount, Duration duration, SubLayer subLayer) {
        super(duration, Layer.PTChangingEffects_7, subLayer, Outcome.BoostCreature);
        this.amount = amount;
        staticText = "{this}'s toughness is equal to the number of " + amount.getMessage();
    }

    public SetBaseToughnessSourceEffect(final SetBaseToughnessSourceEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public SetBaseToughnessSourceEffect copy() {
        return new SetBaseToughnessSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject mageObject = game.getObject(source);
        if (mageObject != null) {
            int value = amount.calculate(game, source, this);
            mageObject.getToughness().setModifiedBaseValue(value);
            return true;
        } else {
            if (duration == Duration.Custom) {
                discard();
            }
        }
        return false;
    }

}
