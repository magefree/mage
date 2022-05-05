
package mage.cards.u;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author JRHerlehy
 */
public final class UntetheredExpress extends CardImpl {

    public UntetheredExpress(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever Untethered Express attacks, put a +1/+1 counter on it.
        this.addAbility(new AttacksTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()).setText("put a +1/+1 counter on it"), false));

        // Crew 1
        this.addAbility(new CrewAbility(1));
    }

    private UntetheredExpress(final UntetheredExpress card) {
        super(card);
    }

    @Override
    public UntetheredExpress copy() {
        return new UntetheredExpress(this);
    }
}
