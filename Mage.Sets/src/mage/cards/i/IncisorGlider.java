package mage.cards.i;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.common.CorruptedCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.constants.AbilityWord;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author TheElk801
 */
public final class IncisorGlider extends CardImpl {

    public IncisorGlider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Corrupted -- Whenever Incisor Glider attacks, if an opponent has three or more poison counters, creatures you control get +1/+1 until end of turn.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new AttacksTriggeredAbility(new BoostControlledEffect(1, 1, Duration.EndOfTurn)),
                CorruptedCondition.instance, "Whenever {this} attacks, if an opponent has three or " +
                "more poison counters, creatures you control get +1/+1 until end of turn."
        ).setAbilityWord(AbilityWord.CORRUPTED).addHint(CorruptedCondition.getHint()));
    }

    private IncisorGlider(final IncisorGlider card) {
        super(card);
    }

    @Override
    public IncisorGlider copy() {
        return new IncisorGlider(this);
    }
}
