package mage.cards.a;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AdventureAwaits extends CardImpl {

    public AdventureAwaits(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        // Look at the top five cards of your library. You may reveal a creature card from among them and put it into your hand.
        // Put the rest on the bottom of your library in a random order. If you don't put a card into your hand this way, draw a card.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(
                5, 1, StaticFilters.FILTER_CARD_CREATURE_A, PutCards.HAND, PutCards.BOTTOM_RANDOM
        ).withOtherwiseEffect(new DrawCardSourceControllerEffect(1)));
    }

    private AdventureAwaits(final AdventureAwaits card) {
        super(card);
    }

    @Override
    public AdventureAwaits copy() {
        return new AdventureAwaits(this);
    }
}
