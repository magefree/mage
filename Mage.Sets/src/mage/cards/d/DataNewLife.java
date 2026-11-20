package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DrawCardControllerTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class DataNewLife extends CardImpl {

    public DataNewLife(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ANDROID);
        this.subtype.add(SubType.SCIENTIST);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // Whenever you draw a card, put a +1/+1 counter on Data.
        this.addAbility(new DrawCardControllerTriggeredAbility(
            new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false
        ));
    }

    private DataNewLife(final DataNewLife card) {
        super(card);
    }

    @Override
    public DataNewLife copy() {
        return new DataNewLife(this);
    }
}
