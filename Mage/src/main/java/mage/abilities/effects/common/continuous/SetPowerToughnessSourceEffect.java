
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
 *
 * @author BetaSteward_at_googlemail.com, North, Alex-Vasile
 */
public class SetPowerToughnessSourceEffect extends ContinuousEffectImpl {

    private DynamicValue power;
    private DynamicValue toughness;
    // If true, the base power/toughness are net, otherwise the final value is set.
    private boolean changeBaseValue;

    public SetPowerToughnessSourceEffect(DynamicValue power, DynamicValue toughness, Duration duration, SubLayer subLayer, boolean changeBaseValue) {
        super(duration, Layer.PTChangingEffects_7, subLayer, Outcome.BoostCreature);
        setCharacterDefining(subLayer == SubLayer.CharacteristicDefining_7a);
        this.power = power;
        this.toughness = toughness;
        this.changeBaseValue = changeBaseValue;
        if (power == toughness) { // When power and toughness are equal, a previous constructor passes the same object for both power nad toughness, so use == instead of .equals
            this.staticText = "{this}'s " + (changeBaseValue ? "base " : "") + "power and toughness are each equal to the number of " + power.getMessage();
        } else {  // The only other constructor creates the power and toughenss dynamic values as static values from passed-in ints.
            String value = (power != null ? power.toString() : toughness.toString());
            this.staticText = "{this}'s " + (changeBaseValue ? "base " : "") + "power and toughness is " + value + '/' + toughness + ' ' + duration.toString();
        }
    }

    public SetPowerToughnessSourceEffect(DynamicValue amount, Duration duration) {
        this(amount, duration, false);
    }

    public SetPowerToughnessSourceEffect(DynamicValue amount, Duration duration, boolean changeBaseValue) {
        this(amount, duration, SubLayer.CharacteristicDefining_7a, changeBaseValue);
    }

    public SetPowerToughnessSourceEffect(DynamicValue amount, Duration duration, SubLayer subLayer) {
        this(amount, duration, subLayer, false);
    }

    public SetPowerToughnessSourceEffect(DynamicValue amount, Duration duration, SubLayer subLayer, boolean changeBaseValue) {
        this(amount, amount, duration, subLayer, false);
    }

    public SetPowerToughnessSourceEffect(int power, int toughness, Duration duration) {
        this(power, toughness, duration, false);
    }

    public SetPowerToughnessSourceEffect(int power, int toughness, Duration duration, boolean changeBaseValue) {
        this(power, toughness, duration, SubLayer.CharacteristicDefining_7a, changeBaseValue);
    }

    public SetPowerToughnessSourceEffect(int power, int toughness, Duration duration, SubLayer subLayer) {
        this(power, toughness, duration, subLayer, false);
    }

    public SetPowerToughnessSourceEffect(int power, int toughness, Duration duration, SubLayer subLayer, boolean changeBaseValue) {
        this(StaticValue.get(power), StaticValue.get(toughness), duration, subLayer, changeBaseValue);
    }

    public SetPowerToughnessSourceEffect(final SetPowerToughnessSourceEffect effect) {
        super(effect);
        this.power = effect.power;
        this.toughness = effect.toughness;
        this.changeBaseValue = effect.changeBaseValue;
    }

    @Override
    public SetPowerToughnessSourceEffect copy() {
        return new SetPowerToughnessSourceEffect(this);
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
        if (mageObject == null) {
            discard();
            return false;
        }

        if (this.power != null) {
            int power = this.power.calculate(game, source, this);
            if (changeBaseValue) {
                mageObject.getPower().setModifiedBaseValue(power);
            } else {
                mageObject.getPower().setBoostedValue(power);
            }
        }

        if (this.toughness != null) {
            int toughness = this.toughness.calculate(game, source, this);
            if (changeBaseValue) {
                mageObject.getToughness().setModifiedBaseValue(toughness);
            } else {
                mageObject.getToughness().setBoostedValue(toughness);
            }

        }
        return true;
    }
}
