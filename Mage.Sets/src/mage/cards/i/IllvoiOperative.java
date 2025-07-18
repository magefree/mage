package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.CastSecondSpellTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class IllvoiOperative extends CardImpl {

    public IllvoiOperative(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.JELLYFISH);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever you cast your second spell each turn, put a +1/+1 counter on this creature.
        this.addAbility(new CastSecondSpellTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(1))
        ));
    }

    private IllvoiOperative(final IllvoiOperative card) {
        super(card);
    }

    @Override
    public IllvoiOperative copy() {
        return new IllvoiOperative(this);
    }
}
