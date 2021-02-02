
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.BuybackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author Jgod
 */
public final class ShatteringPulse extends CardImpl {

    public ShatteringPulse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{R}");

        // Buyback {3}
        this.addAbility(new BuybackAbility("{3}"));
        
        // Destroy target artifact.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetArtifactPermanent());
    }

    private ShatteringPulse(final ShatteringPulse card) {
        super(card);
    }

    @Override
    public ShatteringPulse copy() {
        return new ShatteringPulse(this);
    }
}
