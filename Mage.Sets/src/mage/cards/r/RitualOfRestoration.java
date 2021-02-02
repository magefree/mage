
package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterArtifactCard;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author fireshoes
 */
public final class RitualOfRestoration extends CardImpl {

    public RitualOfRestoration(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{W}");

        // Return target artifact card from your graveyard to your hand.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(new FilterArtifactCard("artifact card from your graveyard")));
    }

    private RitualOfRestoration(final RitualOfRestoration card) {
        super(card);
    }

    @Override
    public RitualOfRestoration copy() {
        return new RitualOfRestoration(this);
    }
}
