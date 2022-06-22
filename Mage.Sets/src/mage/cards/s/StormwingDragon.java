
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MorphAbility;
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
public final class StormwingDragon extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("other Dragon creature you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(SubType.DRAGON.getPredicate());
    }

    public StormwingDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}");
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Megamorph {5}{R}{R}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{5}{R}{R}"), true));

        // When Stormwing Dragon is turned face up, put a +1/+1 counter on each other Dragon creature you control.
        this.addAbility(new TurnedFaceUpSourceTriggeredAbility(new AddCountersAllEffect(CounterType.P1P1.createInstance(), filter), false, false));
    }

    private StormwingDragon(final StormwingDragon card) {
        super(card);
    }

    @Override
    public StormwingDragon copy() {
        return new StormwingDragon(this);
    }
}
