
package mage.cards.f;

import java.util.UUID;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author L_J
 */
public final class FloodOfRecollection extends CardImpl {

    public FloodOfRecollection(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{U}{U}");

        // Return target instant or sorcery card from your graveyard to your hand. Exile Flood of Recollection.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(new FilterInstantOrSorceryCard("instant or sorcery card from your graveyard")));
        this.getSpellAbility().addEffect(ExileSpellEffect.getInstance());
    }

    public FloodOfRecollection(final FloodOfRecollection card) {
        super(card);
    }

    @Override
    public FloodOfRecollection copy() {
        return new FloodOfRecollection(this);
    }
}
