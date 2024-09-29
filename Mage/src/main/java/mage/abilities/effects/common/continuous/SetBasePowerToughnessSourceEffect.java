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
 * @author BetaSteward_at_googlemail.com, North, Alex-Vasile, xenohedron
 */
public class SetBasePowerToughnessSourceEffect extends ContinuousEffectImpl {

    private final DynamicValue power;
    private final DynamicValue toughness;

    /**
     * This constructor is called by the other more specific constructors which set text for appropriate usages.
     * @param power can be null, if only toughness is to be modified
     * @param toughness can be null, if only power is to be modified
     */
    protected SetBasePowerToughnessSourceEffect(DynamicValue power, DynamicValue toughness, Duration duration, SubLayer subLayer) {
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

    /**
     * @param amount Power and toughness to set in layer 7b
     * @param duration Duration for the effect
     */
    public SetBasePowerToughnessSourceEffect(DynamicValue amount, Duration duration) {
        this(amount, amount, duration, SubLayer.SetPT_7b);
        if (duration.toString().isEmpty()) {
            staticText = "{this}'s power and toughness are each equal to the number of " + amount.getMessage();
        } else {
            staticText = "{this} has base power and toughness each equal to the number of " + amount.getMessage() + " " + duration;
        }
    }

    /**
     * @param power set in layer 7b
     * @param toughness set in layer 7b
     * @param duration Duration for the effect
     * @param text Text to set as staticText
     */
    public SetBasePowerToughnessSourceEffect(DynamicValue power, DynamicValue toughness, Duration duration, String text) {
        this(power, toughness, duration, SubLayer.SetPT_7b);
        this.staticText = text;
    }

    /**
     * @param power set in layer 7b
     * @param toughness set in layer 7b
     * @param duration Duration for the effect
     */
    public SetBasePowerToughnessSourceEffect(int power, int toughness, Duration duration) {
        this(StaticValue.get(power), StaticValue.get(toughness), duration, SubLayer.SetPT_7b);
        this.staticText = "{this} has base power and toughness " + power + '/' + toughness + ' ' + duration.toString();
    }

    protected SetBasePowerToughnessSourceEffect(final SetBasePowerToughnessSourceEffect effect) {
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
            if (this.characterDefining || this.duration == Duration.WhileOnBattlefield) {
                // Duration is a workaround for Primal Clay and similar which are incorrectly implemented
                mageObject = game.getObject(source);
            } else {
                mageObject = source.getSourcePermanentIfItStillExists(game);
            }
        }
        if (mageObject == null) {
            discard();
            return false;
        }
        if (this.power != null) {
            mageObject.getPower().setModifiedBaseValue(this.power.calculate(game, source, this));
        }
        if (this.toughness != null) {
            mageObject.getToughness().setModifiedBaseValue(this.toughness.calculate(game, source, this));
        }
        return true;
    }
}
