package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.CardsLeaveGraveyardTriggeredAbility;
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
public final class SpiritMascot extends CardImpl {

    public SpiritMascot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{W}");

        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.OX);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever one or more cards leave your graveyard, put a +1/+1 counter on this creature.
        this.addAbility(new CardsLeaveGraveyardTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance())));
    }

    private SpiritMascot(final SpiritMascot card) {
        super(card);
    }

    @Override
    public SpiritMascot copy() {
        return new SpiritMascot(this);
    }
}
