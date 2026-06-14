package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.common.DrawNthCardTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class BlueMarvelAdamBrashear extends CardImpl {

    public BlueMarvelAdamBrashear(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCIENTIST);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Ward {2}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{2}")));

        // Whenever you draw your second card each turn, put a +1/+1 counter on Blue Marvel.
        this.addAbility(new DrawNthCardTriggeredAbility(
            new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
            false, 2
        ));
    }

    private BlueMarvelAdamBrashear(final BlueMarvelAdamBrashear card) {
        super(card);
    }

    @Override
    public BlueMarvelAdamBrashear copy() {
        return new BlueMarvelAdamBrashear(this);
    }
}
