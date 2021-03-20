package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.LifelinkAbility;
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
 * @author TheElk801
 */
public final class CharmedStray extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("other creature you control named Charmed Stray");

    static {
        filter.add(new NamePredicate("Charmed Stray"));
        filter.add(AnotherPredicate.instance);
    }

    public CharmedStray(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.CAT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Whenever Charmed Stray enters the battlefield, put a +1/+1 counter on each other creature you control named Charmed Stray.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new AddCountersAllEffect(CounterType.P1P1.createInstance(), filter)
        ));
    }

    private CharmedStray(final CharmedStray card) {
        super(card);
    }

    @Override
    public CharmedStray copy() {
        return new CharmedStray(this);
    }
}
