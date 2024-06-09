package mage.cards.d;

import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.CleaveAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DigUp extends CardImpl {

    public DigUp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}");

        // Cleave {1}{B}{B}{G}
        this.addAbility(new CleaveAbility(this, new SearchLibraryPutInHandEffect(new TargetCardInLibrary(), false), "{1}{B}{B}{G}"));

        // Search your library for a [basic land] card, [reveal it,] put it into your hand, then shuffle.
        this.getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(
                new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND_A), true
        ).setText("search your library for a [basic land] card, [reveal it,] put it into your hand, then shuffle"));
    }

    private DigUp(final DigUp card) {
        super(card);
    }

    @Override
    public DigUp copy() {
        return new DigUp(this);
    }
}
