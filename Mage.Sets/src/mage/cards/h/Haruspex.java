package mage.cards.h;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.costs.common.RemoveVariableCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.dynamicvalue.common.RemovedCountersForCostValue;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.mana.DynamicManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Haruspex extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("another creature");

    static {
        filter.add(AnotherPredicate.instance);
    }

    private static final DynamicValue xValue = new CountersSourceCount(CounterType.P1P1);

    public Haruspex(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.TYRANID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Rapacious Hunger -- Whenever another creature dies, put a +1/+1 counter on Haruspex.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false, filter
        ).withFlavorWord("Rapacious Hunger"));

        // Devouring Monster -- {T}, Remove X +1/+1 counters from Haruspex: Add X mana of any one color.
        Ability ability = new DynamicManaAbility(
                Mana.AnyMana(1), RemovedCountersForCostValue.instance, new TapSourceCost(),
                "Add X mana of any one color", true, xValue
        );
        ability.addCost(new RemoveVariableCountersSourceCost(CounterType.P1P1.createInstance()));
        this.addAbility(ability.withFlavorWord("Devouring Monster"));
    }

    private Haruspex(final Haruspex card) {
        super(card);
    }

    @Override
    public Haruspex copy() {
        return new Haruspex(this);
    }
}
