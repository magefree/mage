package mage.cards.i;

import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.common.FilterPlaneswalkerCard;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IgniteTheBeacon extends CardImpl {

    private static final FilterCard filter = new FilterPlaneswalkerCard("planeswalker cards");

    public IgniteTheBeacon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{W}");

        // Search your library for up to to two planeswalker cards, reveal them, put them into your hand, then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(
                new TargetCardInLibrary(0, 2, filter), true
        ));
    }

    private IgniteTheBeacon(final IgniteTheBeacon card) {
        super(card);
    }

    @Override
    public IgniteTheBeacon copy() {
        return new IgniteTheBeacon(this);
    }
}
