package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.DrawNthCardTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KnightsOfDolAmroth extends CardImpl {

    public KnightsOfDolAmroth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever you draw your second card each turn, put a +1/+1 counter on Knights of Dol Amroth.
        this.addAbility(new DrawNthCardTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false, 2
        ));
    }

    private KnightsOfDolAmroth(final KnightsOfDolAmroth card) {
        super(card);
    }

    @Override
    public KnightsOfDolAmroth copy() {
        return new KnightsOfDolAmroth(this);
    }
}
