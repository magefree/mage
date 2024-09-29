package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author tiera3 - modified from CharmedStray
 */
public final class BalothPackhunter extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("other creature you control named Baloth Packhunter");

    static {
        filter.add(new NamePredicate("Baloth Packhunter"));
        filter.add(AnotherPredicate.instance);
    }

    public BalothPackhunter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever Baloth Packhunter enters the battlefield, put two +1/+1 counters on each other creature you control named Baloth Packhunter.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new AddCountersAllEffect(CounterType.P1P1.createInstance(2), filter)
        ));
    }

    private BalothPackhunter(final BalothPackhunter card) {
        super(card);
    }

    @Override
    public BalothPackhunter copy() {
        return new BalothPackhunter(this);
    }
}
