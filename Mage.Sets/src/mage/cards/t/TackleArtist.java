package mage.cards.t;

import mage.MageInt;
import mage.abilities.abilityword.OpusAbility;
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
public final class TackleArtist extends CardImpl {

    public TackleArtist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.SORCERER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Opus -- Whenever you cast an instant or sorcery spell, put a +1/+1 counter on this creature. If five or more mana was spent to cast that spell, put two +1/+1 counters on this creature instead.
        this.addAbility(new OpusAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)),
                "put a +1/+1 counter on this creature. If five or more mana " +
                        "was spent to cast that spell, put two +1/+1 counters on this creature instead", true
        ));
    }

    private TackleArtist(final TackleArtist card) {
        super(card);
    }

    @Override
    public TackleArtist copy() {
        return new TackleArtist(this);
    }
}
