package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.MutatesSourceTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MutateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Vulpikeet extends CardImpl {

    public Vulpikeet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.FOX);
        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Mutate {2}{W}
        this.addAbility(new MutateAbility(this, "{2}{W}"));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever this creature mutates, put a +1/+1 counter on it.
        this.addAbility(new MutatesSourceTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance())
                        .setText("put a +1/+1 counter on it")
        ));
    }

    private Vulpikeet(final Vulpikeet card) {
        super(card);
    }

    @Override
    public Vulpikeet copy() {
        return new Vulpikeet(this);
    }
}
