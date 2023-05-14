
package mage.cards.g;

import java.util.UUID;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterLandCard;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author hanasu
 */
public final class GaeasBounty extends CardImpl {
    
    private static final FilterLandCard filter = new FilterLandCard("Forest cards");
    
    static {
        filter.add(SubType.FOREST.getPredicate());
    }

    public GaeasBounty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{G}");

        // Search your library for up to two Forest cards, reveal those cards, and put them into your hand. Then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(0, 2, filter), false, true));
    }

    private GaeasBounty(final GaeasBounty card) {
        super(card);
    }

    @Override
    public GaeasBounty copy() {
        return new GaeasBounty(this);
    }
}
