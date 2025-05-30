
package mage.cards.p;

import java.util.UUID;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.ReboundAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author fireshoes
 */
public final class ProfoundJourney extends CardImpl {
    
    private static final FilterCard filter = new FilterPermanentCard("permanent card from your graveyard");

    public ProfoundJourney(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{5}{W}{W}");

        // Return target permanent card from your graveyard to the battlefield.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(filter));
        
        // Rebound
        this.addAbility(new ReboundAbility());
    }

    private ProfoundJourney(final ProfoundJourney card) {
        super(card);
    }

    @Override
    public ProfoundJourney copy() {
        return new ProfoundJourney(this);
    }
}
