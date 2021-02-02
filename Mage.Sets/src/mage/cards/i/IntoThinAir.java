
package mage.cards.i;

import java.util.UUID;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.AffinityForArtifactsAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author Plopman
 */
public final class IntoThinAir extends CardImpl {

    public IntoThinAir(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{5}{U}");


        // Affinity for artifacts
        this.addAbility(new AffinityForArtifactsAbility());
        // Return target artifact to its owner's hand.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetArtifactPermanent());
    }

    private IntoThinAir(final IntoThinAir card) {
        super(card);
    }

    @Override
    public IntoThinAir copy() {
        return new IntoThinAir(this);
    }
}
