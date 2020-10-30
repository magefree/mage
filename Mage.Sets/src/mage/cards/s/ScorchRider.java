package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ScorchRider extends CardImpl {

    public ScorchRider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Kicker {1}{R}
        this.addAbility(new KickerAbility("{1}{R}"));

        // When Scorch Rider enters the battlefield, if it was kicked, it gains haste until end of turn.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new GainAbilitySourceEffect(
                        HasteAbility.getInstance(), Duration.EndOfTurn
                )), KickedCondition.instance, "When {this} enters the battlefield, " +
                "if it was kicked, it gains haste until end of turn."
        ));
    }

    private ScorchRider(final ScorchRider card) {
        super(card);
    }

    @Override
    public ScorchRider copy() {
        return new ScorchRider(this);
    }
}
