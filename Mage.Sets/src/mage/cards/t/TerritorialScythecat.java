package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TerritorialScythecat extends CardImpl {

    public TerritorialScythecat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.CAT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Landfall — Whenever a land enters the battlefield under your control, put a +1/+1 counter on Territorial Scythecat.
        this.addAbility(new LandfallAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance())));
    }

    private TerritorialScythecat(final TerritorialScythecat card) {
        super(card);
    }

    @Override
    public TerritorialScythecat copy() {
        return new TerritorialScythecat(this);
    }
}
