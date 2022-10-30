package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCounterCost;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class Hexavus extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("another creature you control");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("another target creature");

    static {
        filter.add(AnotherPredicate.instance);
        filter2.add(AnotherPredicate.instance);
    }

    public Hexavus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{6}");
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Hexavus enters the battlefield with six +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(
                CounterType.P1P1.createInstance(6), true
        ), "with six +1/+1 counters on it"));

        // {1}, Remove a +1/+1 counter from Hexavus: Put a flying counter on another target creature.
        Ability ability = new SimpleActivatedAbility(
                new AddCountersTargetEffect(CounterType.FLYING.createInstance()), new GenericManaCost(1)
        );
        ability.addCost(new RemoveCountersSourceCost(CounterType.P1P1.createInstance()));
        ability.addTarget(new TargetCreaturePermanent(filter2));
        this.addAbility(ability);

        // {1}, Remove a counter from another creature you control: Put a +1/+1 counter on Hexavus.
        Ability ability2 = new SimpleActivatedAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), new GenericManaCost(1)
        );
        ability2.addCost(new RemoveCounterCost(new TargetPermanent(1, 1, filter, true)));
        this.addAbility(ability2);
    }

    private Hexavus(final Hexavus card) {
        super(card);
    }

    @Override
    public Hexavus copy() {
        return new Hexavus(this);
    }
}
