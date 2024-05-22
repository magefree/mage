package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.ArtifactEnteredUnderYourControlCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

/**
 * @author Cguy7777
 */
public class ThePrydwenSteelFlagshipHumanKnightToken extends TokenImpl {

    /**
     * /!\ You need to add ArtifactEnteredControllerWatcher to any card using this token
     */
    public ThePrydwenSteelFlagshipHumanKnightToken() {
        super("Human Knight Token", "2/2 white Human Knight creature token with \"This creature " +
                "gets +2/+2 as long as an artifact entered the battlefield under your control this turn.\"");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.HUMAN, SubType.KNIGHT);
        power = new MageInt(2);
        toughness = new MageInt(2);

        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(2, 2, Duration.WhileOnBattlefield),
                ArtifactEnteredUnderYourControlCondition.instance,
                "This creature gets +2/+2 as long as an artifact entered the battlefield under your control this turn"))
                .addHint(new ConditionHint(ArtifactEnteredUnderYourControlCondition.instance)));
    }

    private ThePrydwenSteelFlagshipHumanKnightToken(final ThePrydwenSteelFlagshipHumanKnightToken token) {
        super(token);
    }

    @Override
    public ThePrydwenSteelFlagshipHumanKnightToken copy() {
        return new ThePrydwenSteelFlagshipHumanKnightToken(this);
    }
}
