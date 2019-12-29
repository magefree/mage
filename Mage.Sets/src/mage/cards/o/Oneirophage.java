package mage.cards.o;

import mage.MageInt;
import mage.abilities.common.DrawCardControllerTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Oneirophage extends CardImpl {

    public Oneirophage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.SQUID);
        this.subtype.add(SubType.ILLUSION);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you draw a card, put a +1/+1 counter on Oneirophage.
        this.addAbility(new DrawCardControllerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false
        ));
    }

    private Oneirophage(final Oneirophage card) {
        super(card);
    }

    @Override
    public Oneirophage copy() {
        return new Oneirophage(this);
    }
}
