

package mage.cards.n;

import java.util.UUID;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterPermanentCard;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class NaturesSpiral extends CardImpl {

    public NaturesSpiral(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{G}");

        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(new FilterPermanentCard("permanent card from your graveyard")));
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
    }

    private NaturesSpiral(final NaturesSpiral card) {
        super(card);
    }

    @Override
    public NaturesSpiral copy() {
        return new NaturesSpiral(this);
    }
}
