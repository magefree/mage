
package mage.cards.g;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.LoseLifeTargetControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author North
 */
public final class GlissasScorn extends CardImpl {

    public GlissasScorn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{G}");

        // Destroy target artifact. Its controller loses 1 life.
        this.getSpellAbility().addTarget(new TargetArtifactPermanent());
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addEffect(new LoseLifeTargetControllerEffect(1));
    }

    private GlissasScorn(final GlissasScorn card) {
        super(card);
    }

    @Override
    public GlissasScorn copy() {
        return new GlissasScorn(this);
    }
}
