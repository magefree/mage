package mage.cards.f;

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
public final class ForgingTheAnchor extends CardImpl {

    public ForgingTheAnchor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}");

        // Look at the top five cards of your library. You may reveal any number of artifact cards from among them and put the revealed cards into your hand. Put the rest on the bottom of your library in a random order.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(
                5, Integer.MAX_VALUE, StaticFilters.FILTER_CARD_ARTIFACTS,
                PutCards.HAND, PutCards.BOTTOM_RANDOM
        ));
    }

    private ForgingTheAnchor(final ForgingTheAnchor card) {
        super(card);
    }

    @Override
    public ForgingTheAnchor copy() {
        return new ForgingTheAnchor(this);
    }
}
