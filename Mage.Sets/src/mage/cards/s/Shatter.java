
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author Loki
 */
public final class Shatter extends CardImpl {
    public Shatter (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{R}");

        // Destroy target artifact.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetArtifactPermanent());
    }

    private Shatter(final Shatter card) {
        super(card);
    }

    @Override
    public Shatter copy() {
        return new Shatter(this);
    }
}
