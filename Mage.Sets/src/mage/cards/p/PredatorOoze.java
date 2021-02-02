
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.DealtDamageAndDiedTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

/**
 *
 * @author BetaSteward
 */
public final class PredatorOoze extends CardImpl {

    public PredatorOoze(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}{G}{G}");
        this.subtype.add(SubType.OOZE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Predator Ooze is indestructible.
        this.addAbility(IndestructibleAbility.getInstance());

        // Whenever Predator Ooze attacks, put a +1/+1 counter on it.
       this.addAbility(new AttacksTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()).setText("put a +1/+1 counter on it"), false));

        // Whenever a creature dealt damage by Predator Ooze this turn dies, put a +1/+1 counter on Predator Ooze.
        this.addAbility(new DealtDamageAndDiedTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance())));
    }

    private PredatorOoze(final PredatorOoze card) {
        super(card);
    }

    @Override
    public PredatorOoze copy() {
        return new PredatorOoze(this);
    }
}
