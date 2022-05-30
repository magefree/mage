
package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class TrapDigger extends CardImpl {

    private static final FilterControlledLandPermanent filter1 = new FilterControlledLandPermanent("a land with a trap counter on it");
    private static final FilterAttackingCreature filter2 = new FilterAttackingCreature("attacking creature without flying");

    static {
        filter1.add(CounterType.TRAP.getPredicate());
        filter2.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public TrapDigger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {2}{W}, {tap}: Put a trap counter on target land you control.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.TRAP.createInstance()), new ManaCostsImpl("{2}{W}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetControlledPermanent(new FilterControlledLandPermanent()));
        this.addAbility(ability);
        // Sacrifice a land with a trap counter on it: Trap Digger deals 3 damage to target attacking creature without flying.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(3), new SacrificeTargetCost(new TargetControlledPermanent(filter1)));
        ability.addTarget(new TargetPermanent(filter2));
        this.addAbility(ability);
    }

    private TrapDigger(final TrapDigger card) {
        super(card);
    }

    @Override
    public TrapDigger copy() {
        return new TrapDigger(this);
    }
}
