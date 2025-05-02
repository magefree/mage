

package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author nantuko
 */
public final class Dismember extends CardImpl {

    public Dismember (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{B/P}{B/P}");

        
        // Target creature gets -5/-5 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(-5, -5, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private Dismember(final Dismember card) {
        super(card);
    }

    @Override
    public Dismember copy() {
        return new Dismember(this);
    }

}
