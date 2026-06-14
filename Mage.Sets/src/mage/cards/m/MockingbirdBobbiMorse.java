package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class MockingbirdBobbiMorse extends CardImpl {

    public MockingbirdBobbiMorse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Mockingbird is dealt damage, put a +1/+1 counter on her.
        this.addAbility(new DealtDamageToSourceTriggeredAbility(
            new AddCountersSourceEffect(CounterType.P1P1.createInstance(1))
                .setText("put a +1/+1 counter on her"), false
        ));
    }

    private MockingbirdBobbiMorse(final MockingbirdBobbiMorse card) {
        super(card);
    }

    @Override
    public MockingbirdBobbiMorse copy() {
        return new MockingbirdBobbiMorse(this);
    }
}
