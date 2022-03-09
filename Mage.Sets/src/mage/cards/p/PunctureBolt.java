
package mage.cards.p;

import java.util.UUID;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class PunctureBolt extends CardImpl {

    public PunctureBolt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{R}");

        // Puncture Bolt deals 1 damage to target creature. Put a -1/-1 counter on that creature.
        this.getSpellAbility().addEffect(new DamageTargetEffect(1));
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.M1M1.createInstance()).setText("put a -1/-1 counter on that creature"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private PunctureBolt(final PunctureBolt card) {
        super(card);
    }

    @Override
    public PunctureBolt copy() {
        return new PunctureBolt(this);
    }
}
