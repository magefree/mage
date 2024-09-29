

package mage.cards.v;

import java.util.UUID;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterPermanentOrSuspendedCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetPermanentOrSuspendedCard;

/**
 *
 * @author L_J
 */
public final class VensersDiffusion extends CardImpl {
    
    private static final FilterPermanentOrSuspendedCard filter = new FilterPermanentOrSuspendedCard("nonland permanent or suspended card");
    
    static {
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
    }

    public VensersDiffusion (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{U}");

        // Return target nonland permanent or suspended card to its owner's hand.
        this.getSpellAbility().addTarget(new TargetPermanentOrSuspendedCard(filter, false));
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
    }

    private VensersDiffusion(final VensersDiffusion card) {
        super(card);
    }

    @Override
    public VensersDiffusion copy() {
        return new VensersDiffusion(this);
    }

}
