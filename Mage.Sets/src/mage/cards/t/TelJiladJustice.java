
package mage.cards.t;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author Plopman
 */
public final class TelJiladJustice extends CardImpl {

    public TelJiladJustice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{G}");

        // Destroy target artifact.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetArtifactPermanent());
        // Scry 2.
        this.getSpellAbility().addEffect(new ScryEffect(2));
    }

    private TelJiladJustice(final TelJiladJustice card) {
        super(card);
    }

    @Override
    public TelJiladJustice copy() {
        return new TelJiladJustice(this);
    }
}
