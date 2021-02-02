
package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author Plopman
 */
public final class RackAndRuin extends CardImpl {

    public RackAndRuin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{R}");


        // Destroy two target artifacts.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetArtifactPermanent(2));
    }

    private RackAndRuin(final RackAndRuin card) {
        super(card);
    }

    @Override
    public RackAndRuin copy() {
        return new RackAndRuin(this);
    }
}
