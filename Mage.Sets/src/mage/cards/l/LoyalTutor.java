package mage.cards.l;

import java.util.UUID;

import mage.abilities.effects.common.search.SearchLibraryPutOnLibraryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author muz
 */
public final class LoyalTutor extends CardImpl {

    private static final FilterCard filter = new FilterCard("planeswalker card");

    static {
        filter.add(CardType.PLANESWALKER.getPredicate());
    }

    public LoyalTutor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Search your library for a planeswalker card, reveal it, then shuffle and put that card on top.
        this.getSpellAbility().addEffect(new SearchLibraryPutOnLibraryEffect(new TargetCardInLibrary(filter), true));
    }

    private LoyalTutor(final LoyalTutor card) {
        super(card);
    }

    @Override
    public LoyalTutor copy() {
        return new LoyalTutor(this);
    }
}
