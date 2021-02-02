
package mage.cards.s;

import java.util.UUID;
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
public final class Scar extends CardImpl {

    public Scar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{B/R}");


        // Put a -1/-1 counter on target creature.]
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.M1M1.createInstance()));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private Scar(final Scar card) {
        super(card);
    }

    @Override
    public Scar copy() {
        return new Scar(this);
    }
}
