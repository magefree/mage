package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.LeavesBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SuperShredder extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("another permanent");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public SuperShredder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.NINJA);
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Menace
        this.addAbility(new MenaceAbility());

        // Whenever another permanent leaves the battlefield, put a +1/+1 counter on Super Shredder.
        this.addAbility(new LeavesBattlefieldAllTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), filter
        ));
    }

    private SuperShredder(final SuperShredder card) {
        super(card);
    }

    @Override
    public SuperShredder copy() {
        return new SuperShredder(this);
    }
}
