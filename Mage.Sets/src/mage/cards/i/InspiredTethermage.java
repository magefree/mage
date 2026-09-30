package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.PutCounterOnPermanentTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.keyword.EmpowerJaceEffect;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class InspiredTethermage extends CardImpl {

    public InspiredTethermage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever you put one or more loyalty counters on a planeswalker, put a +1/+1 counter on this creature.
        this.addAbility(new PutCounterOnPermanentTriggeredAbility(
            new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
            CounterType.LOYALTY,
            StaticFilters.FILTER_PERMANENT_PLANESWALKER
        ));

        // {6}: Empower Jace 2.
        this.addAbility(new SimpleActivatedAbility(new EmpowerJaceEffect(2), new ManaCostsImpl<>("{6}")));
    }

    private InspiredTethermage(final InspiredTethermage card) {
        super(card);
    }

    @Override
    public InspiredTethermage copy() {
        return new InspiredTethermage(this);
    }
}
