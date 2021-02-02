
package mage.cards.m;

import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.effects.keyword.BolsterEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public final class MapTheWastes extends CardImpl {

    public MapTheWastes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{G}");

        // Search your library for a basic land card, put it onto the battlefield tapped, then shuffle your library.
        TargetCardInLibrary target = new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND);
        this.getSpellAbility().addEffect(new SearchLibraryPutInPlayEffect(target, true));
        
        // Bolster 1.
        this.getSpellAbility().addEffect(new BolsterEffect(1));
    }

    private MapTheWastes(final MapTheWastes card) {
        super(card);
    }

    @Override
    public MapTheWastes copy() {
        return new MapTheWastes(this);
    }
}
