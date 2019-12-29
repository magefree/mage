
package mage.cards.s;

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
 * @author LevelX2
 */
public final class SiegehornCeratops extends CardImpl {

    public SiegehornCeratops(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{W}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // <i>Enrage</i> — Whenever Siegehorn Ceratops is dealt damage, put two +1/+1 counters on it.
        this.addAbility(new DealtDamageToSourceTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(2))
                        .setText("put two +1/+1 counter on it"), false, true));
    }

    public SiegehornCeratops(final SiegehornCeratops card) {
        super(card);
    }

    @Override
    public SiegehornCeratops copy() {
        return new SiegehornCeratops(this);
    }
}
