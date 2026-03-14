package mage.cards.e;

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
public final class EPFPointSquad extends CardImpl {

    public EPFPointSquad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R/W}{R/W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Alliance -- Whenever another creature you control enters, put a +1/+1 counter on this creature.
        this.addAbility(new AllianceAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance())));
    }

    private EPFPointSquad(final EPFPointSquad card) {
        super(card);
    }

    @Override
    public EPFPointSquad copy() {
        return new EPFPointSquad(this);
    }
}
