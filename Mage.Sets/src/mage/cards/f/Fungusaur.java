
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

/**
 *
 * @author Plopman
 */
public final class Fungusaur extends CardImpl {

    public Fungusaur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.subtype.add(SubType.FUNGUS);
        this.subtype.add(SubType.DINOSAUR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Fungusaur is dealt damage, put a +1/+1 counter on it.
        this.addAbility(new DealtDamageToSourceTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(1)), false));
    }

    public Fungusaur(final Fungusaur card) {
        super(card);
    }

    @Override
    public Fungusaur copy() {
        return new Fungusaur(this);
    }
}
