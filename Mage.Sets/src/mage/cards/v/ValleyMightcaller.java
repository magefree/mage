package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ValleyMightcaller extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("another Frog, Rabbit, Raccoon, or Squirrel");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(Predicates.or(
                SubType.FROG.getPredicate(),
                SubType.RABBIT.getPredicate(),
                SubType.RACCOON.getPredicate(),
                SubType.SQUIRREL.getPredicate()
        ));
    }

    public ValleyMightcaller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");

        this.subtype.add(SubType.FROG);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever another Frog, Rabbit, Raccoon, or Squirrel you control enters, put a +1/+1 counter on Valley Mightcaller.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), filter
        ));
    }

    private ValleyMightcaller(final ValleyMightcaller card) {
        super(card);
    }

    @Override
    public ValleyMightcaller copy() {
        return new ValleyMightcaller(this);
    }
}
