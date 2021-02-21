package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SupplyRunners extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("other creature you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public SupplyRunners(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.subtype.add(SubType.DOG);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Supply Runners enters the battlefield, put a +1/+1 counter on each other creature you control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new AddCountersAllEffect(CounterType.P1P1.createInstance(), filter)
        ));
    }

    private SupplyRunners(final SupplyRunners card) {
        super(card);
    }

    @Override
    public SupplyRunners copy() {
        return new SupplyRunners(this);
    }
}
