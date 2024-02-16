package mage.cards.g;

import mage.abilities.effects.common.RevealLibraryPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;

import java.util.UUID;

/**
 * @author awjackson
 */
public final class GlacialRevelation extends CardImpl {

    private static final FilterCard filter = new FilterPermanentCard("snow permanent cards");

    static {
        filter.add(SuperType.SNOW.getPredicate());
    }

    public GlacialRevelation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Reveal the top six cards of your library.
        // You may put any number of snow permanent cards from among them into your hand.
        // Put the rest into your graveyard.
        this.getSpellAbility().addEffect(new RevealLibraryPickControllerEffect(
                6, Integer.MAX_VALUE, filter, PutCards.HAND, PutCards.GRAVEYARD));
    }

    private GlacialRevelation(final GlacialRevelation card) {
        super(card);
    }

    @Override
    public GlacialRevelation copy() {
        return new GlacialRevelation(this);
    }
}
