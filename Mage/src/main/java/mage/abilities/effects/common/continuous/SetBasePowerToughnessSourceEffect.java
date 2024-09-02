package mage.abilities.effects.common.continuous;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.*;
import mage.game.Game;

/**
 * @author BetaSteward_at_googlemail.com, North, Alex-Vasile, xenohedron
 */
public class SetBasePowerToughnessSourceEffect extends ContinuousEffectImpl {

    private final DynamicValue power;
    private final DynamicValue toughness;

    /**
     * @param amount Power and toughness to set as a characteristic-defining ability
     */
    public SetBasePowerToughnessSourceEffect(DynamicValue amount) {
        this(amount, amount, Duration.EndOfGame, SubLayer.CharacteristicDefining_7a);
    }

    /**
     * @param amount Power and toughness to set in layer 7b
     * @param duration Duration for the effect
     */
    public SetBasePowerToughnessSourceEffect(DynamicValue amount, Duration duration) {
        this(amount, amount, duration, SubLayer.SetPT_7b);
    }

    /**
     * @param power set in layer 7b
     * @param toughness set in layer 7b
     * @param duration Duration for the effect
     */
    public SetBasePowerToughnessSourceEffect(int power, int toughness, Duration duration) {
        this(StaticValue.get(power), StaticValue.get(toughness), duration, SubLayer.SetPT_7b);
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
     * This constructor is called by the other more specific constructors which set text for appropriate usages.
     * @param power can be null, if only toughness is to be modified
     * @param toughness can be null, if only power is to be modified
     */
    protected SetBasePowerToughnessSourceEffect(DynamicValue power, DynamicValue toughness, Duration duration, SubLayer subLayer) {
        super(duration, Layer.PTChangingEffects_7, subLayer, Outcome.BoostCreature);
        setCharacterDefining(subLayer == SubLayer.CharacteristicDefining_7a);
        this.power = power;
        this.toughness = toughness;
        if (power == null && toughness == null){
            throw new IllegalArgumentException("Power and Toughness cannot both be null");
        }
        if ((power instanceof StaticValue && !(toughness instanceof StaticValue) && toughness != null) ||
                (!(power instanceof StaticValue) && toughness instanceof StaticValue) && power != null){
            throw new IllegalArgumentException("Wrong code usage. Power and Toughness must either both be StaticValue/null, or both be DynamicValue/null. Mixing StaticValue with DynamicValue is not supported.");
        }

        setStaticText(duration);
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

    void setStaticText(Duration duration) {

        if (power == null){
            staticText = getSingleStatText(toughness, "toughness", duration);
            return;
        }
        if (toughness == null){
            staticText = getSingleStatText(power, "power", duration);
            return;
        }

        StringBuilder sb = new StringBuilder("{this}");

        boolean indefiniteDuration = duration.toString().isEmpty() || duration == Duration.EndOfGame;

        if (indefiniteDuration) {
            sb.append("'s power ");
        } else {
            sb.append(" has base power ");
        }

        // Relies on StaticValue messages all being empty
        String powerMessage = power.getMessage(ValuePhrasing.EQUAL_TO);
        String toughnessMessage = toughness.getMessage(ValuePhrasing.EQUAL_TO);

        if (toughnessMessage.equals(powerMessage)) {
            sb.append("and toughness ");
        }

        if (power instanceof StaticValue) {
            // If one is static, the other is static too (static/null value combinations dealt with above)
            sb.append(((StaticValue)power).getValue()).append("/").append(((StaticValue)toughness).getValue());
            sb.append(" ");
        } else {
            if (indefiniteDuration) {
                if (toughnessMessage.equals(powerMessage)) {
                    sb.append("are ");
                } else {
                    sb.append("is ");
                }
            }

            if (toughnessMessage.equals(powerMessage)){
                sb.append("each ");
            }
            sb.append("equal to ").append(powerMessage);
            if (!toughnessMessage.equals(powerMessage)) {
                if (indefiniteDuration) {
                    sb.append("and {this}'s toughness is equal to ").append(toughnessMessage);
                } else {
                    sb.append("and toughness equal to ").append(toughnessMessage);
                }
            }
        }

        if (!indefiniteDuration) {
            sb.append(duration);
        }

        staticText = sb.toString();
    }

    private String getSingleStatText(DynamicValue value, String statName, Duration duration){
        StringBuilder sb = new StringBuilder("{this}");

        boolean indefiniteDuration = duration.toString().isEmpty() || duration == Duration.EndOfGame;

        if (indefiniteDuration) {
            sb.append("'s ");
        } else {
            sb.append(" has base ");
        }

        sb.append(statName).append(" ");

        if (indefiniteDuration) {
            sb.append("is ");
        }

        if (value instanceof StaticValue) {
            sb.append(((StaticValue)value).getValue());
        } else {
            sb.append("equal to ").append(value.getMessage(ValuePhrasing.EQUAL_TO));
        }

        if (!indefiniteDuration) {
            sb.append(" ").append(duration);
        }

        return sb.toString();
    }
}
