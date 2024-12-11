
package mage.cards.e;

import java.util.UUID;
import mage.abilities.effects.common.counter.DistributeCountersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanentAmount;

/**
 *
 * @author fireshoes
 */
public final class ElvenRite extends CardImpl {

    public ElvenRite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{G}");

        // Distribute two +1/+1 counters among one or two target creatures.
        this.getSpellAbility().addEffect(new DistributeCountersEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanentAmount(2, 1));
    }

    private ElvenRite(final ElvenRite card) {
        super(card);
    }

    @Override
    public ElvenRite copy() {
        return new ElvenRite(this);
    }
}
