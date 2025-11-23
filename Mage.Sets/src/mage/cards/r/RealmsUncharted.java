package mage.cards.r;

import mage.abilities.effects.common.search.SearchLibraryForFourDifferentCardsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author North
 */
public final class RealmsUncharted extends CardImpl {

    public RealmsUncharted(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");

        // Search your library for four land cards with different names and reveal them. An opponent chooses two of those cards. Put the chosen cards into your graveyard and the rest into your hand. Then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryForFourDifferentCardsEffect(
                StaticFilters.FILTER_CARD_LANDS, PutCards.HAND, false
        ));
    }

    private RealmsUncharted(final RealmsUncharted card) {
        super(card);
    }

    @Override
    public RealmsUncharted copy() {
        return new RealmsUncharted(this);
    }
}
