
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.counter.DistributeCountersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanentAmount;

/**
 *
 * @author fireshoes
 */
public final class SplendidAgony extends CardImpl {

    public SplendidAgony(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}");

        // Distribute two -1/-1 counters among one or two target creatures.
        getSpellAbility().addEffect(new DistributeCountersEffect(CounterType.M1M1, 2, false, "one or two target creatures"));
        getSpellAbility().addTarget(new TargetCreaturePermanentAmount(2));

    }

    private SplendidAgony(final SplendidAgony card) {
        super(card);
    }

    @Override
    public SplendidAgony copy() {
        return new SplendidAgony(this);
    }
}
