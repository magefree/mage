
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

/**
 *
 * @author TheElk801
 */
public final class SnappingSailback extends CardImpl {

    public SnappingSailback(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Enrage — Whenever Snapping Sailback is dealt damage, put a +1/+1 counter on it.
        this.addAbility(new DealtDamageToSourceTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(1))
                        .setText("put a +1/+1 counter on it"), false, true));
    }

    public SnappingSailback(final SnappingSailback card) {
        super(card);
    }

    @Override
    public SnappingSailback copy() {
        return new SnappingSailback(this);
    }
}
