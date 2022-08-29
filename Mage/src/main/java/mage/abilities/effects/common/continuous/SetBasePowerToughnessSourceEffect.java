
package mage.abilities.effects.common.continuous;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;

/**
 * RENAME
 * @author BetaSteward_at_googlemail.com, North, Alex-Vasile
 */
public class SetBasePowerToughnessSourceEffect extends ContinuousEffectImpl {

    private DynamicValue power;
    private DynamicValue toughness;

    /**
     *
     * @param power
     * @param toughness
     * @param duration
     * @param subLayer
     * @param baseInText    Whether or not the rules text should refer to "base power and toughness" or "power and toughness"
     *                      Either way, it is always the based power and toughness that are set.
     */
    public SetBasePowerToughnessSourceEffect(DynamicValue power, DynamicValue toughness, Duration duration, SubLayer subLayer, boolean baseInText) {
        super(duration, Layer.PTChangingEffects_7, subLayer, Outcome.BoostCreature);
        setCharacterDefining(subLayer == SubLayer.CharacteristicDefining_7a);
        this.power = power;
        this.toughness = toughness;
        if (power == toughness) { // When power and toughness are equal, a previous constructor passes the same object for both power nad toughness, so use == instead of .equals
            this.staticText = "{this}'s " + (baseInText ? "base " : "") + "power and toughness are each equal to the number of " + power.getMessage();
        } else {  // The only other constructor creates the power and toughenss dynamic values as static values from passed-in ints.
            String value = (power != null ? power.toString() : toughness.toString());
            this.staticText = "{this}'s " + (baseInText ? "base " : "") + "power and toughness is " + value + '/' + toughness + ' ' + duration.toString();
        }
    }

    public SetBasePowerToughnessSourceEffect(DynamicValue amount, Duration duration) {
        this(amount, duration, SubLayer.CharacteristicDefining_7a, false);
    }

    public SetBasePowerToughnessSourceEffect(DynamicValue amount, Duration duration, SubLayer subLayer) {
        this(amount, duration, subLayer, true);
    }

    public SetBasePowerToughnessSourceEffect(DynamicValue amount, Duration duration, SubLayer subLayer, boolean changeBaseValue) {
        this(amount, amount, duration, subLayer, changeBaseValue);
    }

    public SetBasePowerToughnessSourceEffect(int power, int toughness, Duration duration, boolean changeBaseValue) {
        this(power, toughness, duration, SubLayer.CharacteristicDefining_7a, changeBaseValue);
    }

    public SetBasePowerToughnessSourceEffect(int power, int toughness, Duration duration, SubLayer subLayer) {
        this(power, toughness, duration, subLayer, false);
    }

    public SetBasePowerToughnessSourceEffect(int power, int toughness, Duration duration, SubLayer subLayer, boolean changeBaseValue) {
        this(StaticValue.get(power), StaticValue.get(toughness), duration, subLayer, changeBaseValue);
    }

    public SetBasePowerToughnessSourceEffect(final SetBasePowerToughnessSourceEffect effect) {
        super(effect);
        this.power = effect.power;
        this.toughness = effect.toughness;
    }

    @Override
    public SetBasePowerToughnessSourceEffect copy() {
        return new SetBasePowerToughnessSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject mageObject = game.getPermanentEntering(source.getSourceId());
        if (mageObject == null) {
            if (duration == Duration.Custom || isTemporary()) {
                mageObject = game.getPermanent(source.getSourceId());
            } else {
                mageObject = game.getObject(source);
            }
        }
        if (mageObject == null || (power == null && toughness == null)) {
            discard();
            return false;
        }

        if (this.power != null) {
            int power = this.power.calculate(game, source, this);
            mageObject.getPower().setModifiedBaseValue(power);
        }

        if (this.toughness != null) {
            int toughness = this.toughness.calculate(game, source, this);
            mageObject.getToughness().setModifiedBaseValue(toughness);
        }
        return true;
    }
}
