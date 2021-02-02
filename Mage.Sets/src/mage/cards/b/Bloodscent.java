
package mage.cards.b;

import java.util.UUID;
import mage.abilities.effects.common.combat.MustBeBlockedByAllTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class Bloodscent extends CardImpl {

    public Bloodscent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{G}");


        // All creatures able to block target creature this turn do so.
        this.getSpellAbility().addEffect(new MustBeBlockedByAllTargetEffect(Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private Bloodscent(final Bloodscent card) {
        super(card);
    }

    @Override
    public Bloodscent copy() {
        return new Bloodscent(this);
    }
}
