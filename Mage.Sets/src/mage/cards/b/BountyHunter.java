package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author LoneFox
 */
public final class BountyHunter extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with a bounty counter on it");

    static {
        filter.add(CounterType.BOUNTY.getPredicate());
    }

    public BountyHunter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{B}");
        this.subtype.add(SubType.HUMAN, SubType.ARCHER, SubType.MINION);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {tap}: Put a bounty counter on target nonblack creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.BOUNTY.createInstance()), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_PERMANENT_CREATURE_NON_BLACK));
        this.addAbility(ability);
        // {tap}: Destroy target creature with a bounty counter on it.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    private BountyHunter(final BountyHunter card) {
        super(card);
    }

    @Override
    public BountyHunter copy() {
        return new BountyHunter(this);
    }
}
