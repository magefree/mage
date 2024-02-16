
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.ReplicateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author LevelX2
 */
public final class ShatteringSpree extends CardImpl {

    public ShatteringSpree(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{R}");

        // Replicate {R}
        this.addAbility(new ReplicateAbility("{R}"));
        // Destroy target artifact.
        this.getSpellAbility().addEffect(new DestroyTargetEffect(false));
        this.getSpellAbility().addTarget(new TargetArtifactPermanent());
    }

    private ShatteringSpree(final ShatteringSpree card) {
        super(card);
    }

    @Override
    public ShatteringSpree copy() {
        return new ShatteringSpree(this);
    }
}
