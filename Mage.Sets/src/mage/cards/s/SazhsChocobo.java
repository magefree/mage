package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.LandfallAbility;
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
public final class SazhsChocobo extends CardImpl {

    public SazhsChocobo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");

        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // Landfall -- Whenever a land you control enters, put a +1/+1 counter on this creature.
        this.addAbility(new LandfallAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance())));
    }

    private SazhsChocobo(final SazhsChocobo card) {
        super(card);
    }

    @Override
    public SazhsChocobo copy() {
        return new SazhsChocobo(this);
    }
}
