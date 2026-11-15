package mage.cards.w;

import java.util.UUID;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.effects.keyword.EmpowerJaceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class WayOfTheMentor extends CardImpl {

    public WayOfTheMentor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        this.supertype.add(SuperType.LEGENDARY);

        // When Way of the Mentor enters, empower Jace 5.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new EmpowerJaceEffect(5)));

        // Whenever you gain life, put a loyalty counter on each planeswalker you control.
        this.addAbility(new GainLifeControllerTriggeredAbility(
            new AddCountersAllEffect(
                CounterType.LOYALTY.createInstance(),
                StaticFilters.FILTER_CONTROLLED_PERMANENT_PLANESWALKER
            )
        ));
    }

    private WayOfTheMentor(final WayOfTheMentor card) {
        super(card);
    }

    @Override
    public WayOfTheMentor copy() {
        return new WayOfTheMentor(this);
    }
}
