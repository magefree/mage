package mage.cards.r;

import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ReshapeTheEarth extends CardImpl {

    public ReshapeTheEarth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{6}{G}{G}{G}");

        // Search your library for up to ten land cards, put them onto the battlefield tapped, then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(
                0, 10, StaticFilters.FILTER_CARD_LANDS
        ), true));
    }

    private ReshapeTheEarth(final ReshapeTheEarth card) {
        super(card);
    }

    @Override
    public ReshapeTheEarth copy() {
        return new ReshapeTheEarth(this);
    }
}
