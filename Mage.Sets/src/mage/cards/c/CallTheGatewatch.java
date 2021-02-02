
package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterPlaneswalkerCard;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author fireshoes
 */
public final class CallTheGatewatch extends CardImpl {
    
    private static final FilterPlaneswalkerCard filter = new FilterPlaneswalkerCard("planeswalker card");

    public CallTheGatewatch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{W}");

        // Search your library for a planeswalker card, reveal it, and put it into your hand. Then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true));
    }

    private CallTheGatewatch(final CallTheGatewatch card) {
        super(card);
    }

    @Override
    public CallTheGatewatch copy() {
        return new CallTheGatewatch(this);
    }
}
