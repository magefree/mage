package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.KickerAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BogBadger extends CardImpl {

    public BogBadger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.BADGER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Kicker {B}
        this.addAbility(new KickerAbility("{B}"));

        // When Bog Badger enters the battlefield, if it was kicked, creatures you control gain menace until end of turn.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new GainAbilityControlledEffect(
                        new MenaceAbility(false), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES
                )), KickedCondition.ONCE, "When {this} enters the battlefield, " +
                "if it was kicked, creatures you control gain menace until end of turn. " +
                "<i>(A creature with menace can't be blocked except by two or more creatures.)</i>"
        ));
    }

    private BogBadger(final BogBadger card) {
        super(card);
    }

    @Override
    public BogBadger copy() {
        return new BogBadger(this);
    }
}
