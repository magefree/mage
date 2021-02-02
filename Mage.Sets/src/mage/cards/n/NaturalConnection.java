
package mage.cards.n;

import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class NaturalConnection extends CardImpl {

    public NaturalConnection(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{G}");

        // Search your library for a basic land card, put it onto the battlefield tapped, then shuffle your library.
        TargetCardInLibrary target = new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND);
        this.getSpellAbility().addEffect(new SearchLibraryPutInPlayEffect(target, true));

    }

    private NaturalConnection(final NaturalConnection card) {
        super(card);
    }

    @Override
    public NaturalConnection copy() {
        return new NaturalConnection(this);
    }
}
