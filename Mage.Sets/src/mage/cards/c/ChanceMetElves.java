package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.ScryTriggeredAbility;
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
public final class ChanceMetElves extends CardImpl {

    public ChanceMetElves(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever you scry, put a +1/+1 counter on Chance-Met Elves. This ability triggers only once each turn.
        this.addAbility(new ScryTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance())).setTriggersOnceEachTurn(true));
    }

    private ChanceMetElves(final ChanceMetElves card) {
        super(card);
    }

    @Override
    public ChanceMetElves copy() {
        return new ChanceMetElves(this);
    }
}
