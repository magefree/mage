
package mage.cards.q;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesBlockedByCreatureTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

/**
 *
 * @author fireshoes
 */
public final class QuagmireLamprey extends CardImpl {

    public QuagmireLamprey(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.FISH);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Quagmire Lamprey becomes blocked by a creature, put a -1/-1 counter on that creature.
        Effect effect = new AddCountersTargetEffect(CounterType.M1M1.createInstance(1));
        effect.setText("put a -1/-1 counter on that creature");
        this.addAbility(new BecomesBlockedByCreatureTriggeredAbility(effect, false));
    }

    private QuagmireLamprey(final QuagmireLamprey card) {
        super(card);
    }

    @Override
    public QuagmireLamprey copy() {
        return new QuagmireLamprey(this);
    }
}
