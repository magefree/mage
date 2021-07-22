package mage.cards.f;

import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author Loki
 */
public final class Fabricate extends CardImpl {

    public Fabricate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}");

        // Search your library for an artifact card, reveal it, and put it into your hand. Then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(
                new TargetCardInLibrary(1, StaticFilters.FILTER_CARD_ARTIFACT), true
        ));
    }

    private Fabricate(final Fabricate card) {
        super(card);
    }

    @Override
    public Fabricate copy() {
        return new Fabricate(this);
    }
}
