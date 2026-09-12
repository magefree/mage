package mage.cards.w;

import java.util.UUID;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.effects.keyword.EmpowerJaceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class WayOfTheNecromancer extends CardImpl {

    public WayOfTheNecromancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");

        this.supertype.add(SuperType.LEGENDARY);

        // When Way of the Necromancer enters, empower Jace 2.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new EmpowerJaceEffect(2)));

        // Whenever a creature you control dies, put a loyalty counter on each planeswalker you control.
        this.addAbility(new DiesCreatureTriggeredAbility(
            new AddCountersAllEffect(CounterType.LOYALTY.createInstance(), StaticFilters.FILTER_CONTROLLED_PERMANENT_PLANESWALKER),
            false,
            StaticFilters.FILTER_CONTROLLED_CREATURE
        ));
    }

    private WayOfTheNecromancer(final WayOfTheNecromancer card) {
        super(card);
    }

    @Override
    public WayOfTheNecromancer copy() {
        return new WayOfTheNecromancer(this);
    }
}
