package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;

/**
 *
 * @author muz
 */
public final class AbominationIrradiatedBrute extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("a Gamma or Villain you control");

    static {
        filter.add(Predicates.or(
            SubType.GAMMA.getPredicate(),
            SubType.VILLAIN.getPredicate()
        ));
    }

    public AbominationIrradiatedBrute(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GAMMA);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever a Gamma or Villain you control deals combat damage to a player, put that many +1/+1 counters on Abomination.
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(
            new AddCountersSourceEffect(CounterType.P1P1.createInstance(), SavedDamageValue.MANY)
                .setText("put that many +1/+1 counters on Abomination"),
            filter, false, SetTargetPointer.NONE, true, false
        ));
    }

    private AbominationIrradiatedBrute(final AbominationIrradiatedBrute card) {
        super(card);
    }

    @Override
    public AbominationIrradiatedBrute copy() {
        return new AbominationIrradiatedBrute(this);
    }
}
