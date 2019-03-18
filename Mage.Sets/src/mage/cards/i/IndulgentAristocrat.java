
package mage.cards.i;

import java.util.UUID;
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
import static mage.filter.StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class IndulgentAristocrat extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Vampire you control");

    static {
        filter.add(new SubtypePredicate(SubType.VAMPIRE));
    }

    public IndulgentAristocrat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");
        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // {2}, Sacrifice a creature: Put a +1/+1 counter on each Vampire you control.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersAllEffect(CounterType.P1P1.createInstance(), filter), new GenericManaCost(2));
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(FILTER_CONTROLLED_CREATURE_SHORT_TEXT)));
        this.addAbility(ability);
    }

    public IndulgentAristocrat(final IndulgentAristocrat card) {
        super(card);
    }

    @Override
    public IndulgentAristocrat copy() {
        return new IndulgentAristocrat(this);
    }
}
