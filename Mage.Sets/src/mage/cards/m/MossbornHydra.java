package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.DoubleCountersSourceEffect;
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
public final class MossbornHydra extends CardImpl {

    public MossbornHydra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.HYDRA);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // This creature enters with a +1/+1 counter on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(1)), "with a +1/+1 counter on it"));

        // Landfall -- Whenever a land you control enters, double the number of +1/+1 counters on this creature.
        this.addAbility(new LandfallAbility(new DoubleCountersSourceEffect(CounterType.P1P1)));
    }

    private MossbornHydra(final MossbornHydra card) {
        super(card);
    }

    @Override
    public MossbornHydra copy() {
        return new MossbornHydra(this);
    }
}
