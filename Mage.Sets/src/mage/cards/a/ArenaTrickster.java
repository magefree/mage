package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.FirstSpellOpponentsTurnTriggeredAbility;
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
public final class ArenaTrickster extends CardImpl {

    public ArenaTrickster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever you cast your first spell during each opponent's turn, put a +1/+1 counter on Arena Trickster.
        this.addAbility(new FirstSpellOpponentsTurnTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false
        ));
    }

    private ArenaTrickster(final ArenaTrickster card) {
        super(card);
    }

    @Override
    public ArenaTrickster copy() {
        return new ArenaTrickster(this);
    }
}
