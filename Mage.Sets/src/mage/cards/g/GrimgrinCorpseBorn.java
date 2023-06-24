package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DontUntapInControllersUntapStepSourceEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.DefendingPlayerControlsPredicate;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author North, xenohedron
 */
public final class GrimgrinCorpseBorn extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature defending player controls");

    static {
        filter.add(DefendingPlayerControlsPredicate.instance);
    }

    public GrimgrinCorpseBorn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Grimgrin, Corpse-Born enters the battlefield tapped and doesn't untap during your untap step.
        Ability ability = new EntersBattlefieldTappedAbility(
                "{this} enters the battlefield tapped and doesn't untap during your untap step.");
        ability.addEffect(new DontUntapInControllersUntapStepSourceEffect());
        this.addAbility(ability);

        // Sacrifice another creature: Untap Grimgrin and put a +1/+1 counter on it.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new UntapSourceEffect(),
                new SacrificeTargetCost(new TargetControlledCreaturePermanent(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE)));
        ability.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance()).setText("and put a +1/+1 counter on it"));
        this.addAbility(ability);

        // Whenever Grimgrin attacks, destroy target creature defending player controls, then put a +1/+1 counter on Grimgrin.
        ability = new AttacksTriggeredAbility(new DestroyTargetEffect());
        ability.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance()).concatBy(", then"));
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    private GrimgrinCorpseBorn(final GrimgrinCorpseBorn card) {
        super(card);
    }

    @Override
    public GrimgrinCorpseBorn copy() {
        return new GrimgrinCorpseBorn(this);
    }
}
