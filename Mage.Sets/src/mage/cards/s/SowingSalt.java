
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.ExileTargetAndSearchGraveyardHandLibraryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetNonBasicLandPermanent;

/**
 *
 * @author LevelX2
 */
public final class SowingSalt extends CardImpl {

    public SowingSalt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{R}{R}");


        // Exile target nonbasic land. Search its controller's graveyard, hand, and library for all cards with 
        // the same name as that land and exile them. Then that player shuffles their library.
        this.getSpellAbility().addTarget(new TargetNonBasicLandPermanent());
        this.getSpellAbility().addEffect(new ExileTargetAndSearchGraveyardHandLibraryEffect(false, "its controller's","all cards with the same name as that land"));
    }

    private SowingSalt(final SowingSalt card) {
        super(card);
    }

    @Override
    public SowingSalt copy() {
        return new SowingSalt(this);
    }
}
    
 