package mage.cards.p;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.SubType;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;

/**
 * @author TheElk801
 */
public final class ProwlingFelidar extends CardImpl {

    public ProwlingFelidar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Landfall — Whenever a land enters the battlefield under your control, put a +1/+1 counter on Prowling Felidar.
        this.addAbility(new LandfallAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance())));
    }

    private ProwlingFelidar(final ProwlingFelidar card) {
        super(card);
    }

    @Override
    public ProwlingFelidar copy() {
        return new ProwlingFelidar(this);
    }
}
