package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.LoseLifeFirstTimeEachTurnTriggeredAbility;
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
public final class VengefulWarchief extends CardImpl {

    public VengefulWarchief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever you lose life for the first time each turn, put a +1/+1 counter on Vengeful Warchief.
        this.addAbility(new LoseLifeFirstTimeEachTurnTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance())));
    }

    private VengefulWarchief(final VengefulWarchief card) {
        super(card);
    }

    @Override
    public VengefulWarchief copy() {
        return new VengefulWarchief(this);
    }
}
