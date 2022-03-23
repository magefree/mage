
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
 * @author BetaSteward_at_googlemail.com, North
 */
public class SetPowerToughnessSourceEffect extends ContinuousEffectImpl {

    private DynamicValue amount;
    private int power;
    private int toughness;

    public SetPowerToughnessSourceEffect(DynamicValue amount, Duration duration) {
        this(amount, duration, SubLayer.CharacteristicDefining_7a);
    }

    public SetPowerToughnessSourceEffect(DynamicValue amount, Duration duration, SubLayer subLayer) {
        super(duration, Layer.PTChangingEffects_7, subLayer, Outcome.BoostCreature);
        setCharacterDefining(subLayer == SubLayer.CharacteristicDefining_7a);
        this.amount = amount;
        staticText = "{this}'s power and toughness are each equal to the number of " + amount.getMessage();
    }

    public SetPowerToughnessSourceEffect(int power, int toughness, Duration duration) {
        this(power, toughness, duration, SubLayer.CharacteristicDefining_7a);
    }

    public SetPowerToughnessSourceEffect(int power, int toughness, Duration duration, SubLayer subLayer) {
        super(duration, Layer.PTChangingEffects_7, subLayer, Outcome.BoostCreature);
        this.power = power;
        this.toughness = toughness;
        staticText = "{this}'s power and toughness is " + power + '/' + toughness + ' ' + duration.toString();
    }

    public SetPowerToughnessSourceEffect(final SetPowerToughnessSourceEffect effect) {
        super(effect);
        this.amount = effect.amount;
        this.power = effect.power;
        this.toughness = effect.toughness;
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
            return true;
        }
        if (amount != null) {
            int value = amount.calculate(game, source, this);
            mageObject.getPower().setValue(value);
            mageObject.getToughness().setValue(value);
            return true;
        } else {
            if (power != Integer.MIN_VALUE) {
                mageObject.getPower().setValue(power);
            }
            if (toughness != Integer.MIN_VALUE) {
                mageObject.getToughness().setValue(toughness);
            }
        }
        return true;
    }
}
