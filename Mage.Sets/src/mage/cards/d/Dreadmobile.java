package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class Dreadmobile extends CardImpl {

    public Dreadmobile(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{B}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility());

        // {1}, Sacrifice another artifact or creature: Put a +1/+1 counter on Dreadmobile.
        Ability ability = new SimpleActivatedAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), new GenericManaCost(1)
        );
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE_OR_ARTIFACT));
        this.addAbility(ability);

        // Crew 1
        this.addAbility(new CrewAbility(1));

    }

    private Dreadmobile(final Dreadmobile card) {
        super(card);
    }

    @Override
    public Dreadmobile copy() {
        return new Dreadmobile(this);
    }
}
