package mage.cards.g;

import mage.abilities.effects.common.search.SearchLibraryForFourDifferentCardsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.filter.StaticFilters;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class GiftsUngiven extends CardImpl {

    public GiftsUngiven(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}");

        // Search your library for up to four cards with different names and reveal them. Target opponent chooses two of those cards. Put the chosen cards into your graveyard and the rest into your hand. Then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryForFourDifferentCardsEffect(
                StaticFilters.FILTER_CARD_CARDS, PutCards.HAND, true
        ));
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private GiftsUngiven(final GiftsUngiven card) {
        super(card);
    }

    @Override
    public GiftsUngiven copy() {
        return new GiftsUngiven(this);
    }
}
