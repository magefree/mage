
package mage.cards.b;

import java.util.UUID;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Plopman
 */
public final class BurstOfStrength extends CardImpl {

    public BurstOfStrength(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{G}");


        // Put a +1/+1 counter on target creature and untap it.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance(1)));
        this.getSpellAbility().addEffect(new UntapTargetEffect().setText("and untap it"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private BurstOfStrength(final BurstOfStrength card) {
        super(card);
    }

    @Override
    public BurstOfStrength copy() {
        return new BurstOfStrength(this);
    }
}
