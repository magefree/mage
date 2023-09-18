package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.dynamicvalue.common.ManaSpentToCastCount;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KurbisHarvestCelebrant extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("another creature with a +1/+1 counter on it");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(CounterType.P1P1.getPredicate());
    }

    public KurbisHarvestCelebrant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TREEFOLK);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Kurbis, Harvest Celebrant enters the battlefield with a number of +1/+1 counters on it equal to the amount of mana spent to cast it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(
                CounterType.P1P1.createInstance(), ManaSpentToCastCount.instance, true
        ), "with a number of +1/+1 counters on it equal to the amount of mana spent to cast it"));

        // Remove a +1/+1 counter from Kurbis: Prevent all damage that would be dealt this turn to another target creature with a +1/+1 counter on it.
        Ability ability = new SimpleActivatedAbility(
                new PreventDamageToTargetEffect(Duration.EndOfTurn)
                        .setText("prevent all damage that would be dealt this turn " +
                                "to another target creature with a +1/+1 counter on it"),
                new RemoveCountersSourceCost(CounterType.P1P1.createInstance())
        );
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private KurbisHarvestCelebrant(final KurbisHarvestCelebrant card) {
        super(card);
    }

    @Override
    public KurbisHarvestCelebrant copy() {
        return new KurbisHarvestCelebrant(this);
    }
}
