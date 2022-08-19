package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.SacrificeAllEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BenalishSleeper extends CardImpl {

    public BenalishSleeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Kicker {B}
        this.addAbility(new KickerAbility("{B}"));

        // When Benalish Sleeper enters the battlefield, if it was kicked, each player sacrifices a creature.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(new EntersBattlefieldTriggeredAbility(
                new SacrificeAllEffect(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT)
        ), KickedCondition.instance, "When {this} enters the battlefield, " +
                "if it was kicked, each player sacrifices a creature."));
    }

    private BenalishSleeper(final BenalishSleeper card) {
        super(card);
    }

    @Override
    public BenalishSleeper copy() {
        return new BenalishSleeper(this);
    }
}
