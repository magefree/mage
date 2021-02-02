
package mage.cards.a;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author magenoxx_at_gmail.com
 */
public final class AugerSpree extends CardImpl {

    public AugerSpree(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{B}{R}");


        // Target creature gets +4/-4 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(4, -4, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private AugerSpree(final AugerSpree card) {
        super(card);
    }

    @Override
    public AugerSpree copy() {
        return new AugerSpree(this);
    }
}
