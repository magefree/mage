package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

import mage.filter.StaticFilters;

/**
 * @author fireshoes
 */
public final class IndulgentAristocrat extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Vampire you control");

    static {
        filter.add(SubType.VAMPIRE.getPredicate());
    }

    public IndulgentAristocrat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");
        this.subtype.add(SubType.VAMPIRE, SubType.NOBLE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // {2}, Sacrifice a creature: Put a +1/+1 counter on each Vampire you control.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersAllEffect(CounterType.P1P1.createInstance(), filter), new GenericManaCost(2));
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT));
        this.addAbility(ability);
    }

    private IndulgentAristocrat(final IndulgentAristocrat card) {
        super(card);
    }

    @Override
    public IndulgentAristocrat copy() {
        return new IndulgentAristocrat(this);
    }
}
