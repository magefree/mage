

package mage.cards.i;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class InstillInfection extends CardImpl {

    public InstillInfection (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{B}");

        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.M1M1.createInstance()));
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public InstillInfection (final InstillInfection card) {
        super(card);
    }

    @Override
    public InstillInfection copy() {
        return new InstillInfection(this);
    }

}
