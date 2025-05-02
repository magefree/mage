package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 *
 * @author ciaccona007
 */
public final class HungryGhoul extends CardImpl {

    public HungryGhoul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");
        
        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {1}, Sacrifice another creature: Put a +1/+1 counter on this creature.
        Ability ability = new SimpleActivatedAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), new GenericManaCost(1)
        );
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE));
        this.addAbility(ability);
    }

    private HungryGhoul(final HungryGhoul card) {
        super(card);
    }

    @Override
    public HungryGhoul copy() {
        return new HungryGhoul(this);
    }
}
