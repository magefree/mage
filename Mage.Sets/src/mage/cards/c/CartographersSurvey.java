package mage.cards.c;

import java.util.UUID;

import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.filter.StaticFilters;

/**
 *
 * @author weirddan455
 */
public final class CartographersSurvey extends CardImpl {

    public CartographersSurvey(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}");

        // Look at the top seven cards of your library. Put up to two land cards from among them onto the battlefield tapped. Put the rest on the bottom of your library in a random order.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(
                7, 2, StaticFilters.FILTER_CARD_LANDS, PutCards.BATTLEFIELD_TAPPED, PutCards.BOTTOM_RANDOM, false));
    }

    private CartographersSurvey(final CartographersSurvey card) {
        super(card);
    }

    @Override
    public CartographersSurvey copy() {
        return new CartographersSurvey(this);
    }
}
