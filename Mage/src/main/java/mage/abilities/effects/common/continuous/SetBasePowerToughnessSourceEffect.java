
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
 * @author BetaSteward_at_googlemail.com, North, Alex-Vasile, xenohedron
 */
public class SetBasePowerToughnessSourceEffect extends ContinuousEffectImpl {

    private final DynamicValue power;
    private final DynamicValue toughness;

    /**
     * Note: Need to set text manually if calling this constructor directly
     */
    public SetBasePowerToughnessSourceEffect(DynamicValue power, DynamicValue toughness, Duration duration, SubLayer subLayer) {
        super(duration, Layer.PTChangingEffects_7, subLayer, Outcome.BoostCreature);
        setCharacterDefining(subLayer == SubLayer.CharacteristicDefining_7a);
        this.power = power;
        this.toughness = toughness;
    }

    /**
     * @param amount Power and toughness to set as a characteristic-defining ability
     */
    public SetBasePowerToughnessSourceEffect(DynamicValue amount) {
        this(amount, amount, Duration.EndOfGame, SubLayer.CharacteristicDefining_7a);
        this.staticText = "{this}'s power and toughness are each equal to the number of " + amount.getMessage();
    }

    public SetBasePowerToughnessSourceEffect(int power, int toughness, Duration duration, SubLayer subLayer) {
        this(StaticValue.get(power), StaticValue.get(toughness), duration, subLayer);
        this.staticText = "{this} has base power and toughness " + power + '/' + toughness + ' ' + duration.toString();
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
