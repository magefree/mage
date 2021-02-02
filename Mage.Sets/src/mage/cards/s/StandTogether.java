
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class StandTogether extends CardImpl {

    public StandTogether(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{G}{G}");

        // Put two +1/+1 counters on target creature and two +1/+1 counters on another target creature.
        Effect effect = new AddCountersTargetEffect(CounterType.P1P1.createInstance(2));
        effect.setText("Put two +1/+1 counters on target creature and two +1/+1 counters on another target creature");
        this.getSpellAbility().addEffect(effect);
        Target target = new TargetCreaturePermanent(2,2);
        this.getSpellAbility().addTarget(target);
    }

    private StandTogether(final StandTogether card) {
        super(card);
    }

    @Override
    public StandTogether copy() {
        return new StandTogether(this);
    }
}
