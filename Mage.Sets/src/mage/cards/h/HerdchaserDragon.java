
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MorphAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author fireshoes
 */
public final class HerdchaserDragon extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("other Dragon creature you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(SubType.DRAGON.getPredicate());
    }

    public HerdchaserDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}");
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Megamorph {5}{G}{G}
        this.addAbility(new MorphAbility(new ManaCostsImpl("{5}{G}{G}"), true));

        // When Herdchaser Dragon is turned face up, put a +1/+1 counter on each other Dragon creature you control.
        this.addAbility(new TurnedFaceUpSourceTriggeredAbility(new AddCountersAllEffect(CounterType.P1P1.createInstance(), filter), false, false));
    }

    private HerdchaserDragon(final HerdchaserDragon card) {
        super(card);
    }

    @Override
    public HerdchaserDragon copy() {
        return new HerdchaserDragon(this);
    }
}
