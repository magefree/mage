package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.AllianceAbility;
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
public final class CelebrityFencer extends CardImpl {

    public CelebrityFencer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Alliance — Whenever another creature enters the battlefield under your control, put a +1/+1 counter on Celebrity Fencer.
        this.addAbility(new AllianceAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance())));
    }

    private CelebrityFencer(final CelebrityFencer card) {
        super(card);
    }

    @Override
    public CelebrityFencer copy() {
        return new CelebrityFencer(this);
    }
}
