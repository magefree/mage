
package mage.cards.s;

import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 *
 * @author Loki
 */
public final class SeekTheHorizon extends CardImpl {

    public SeekTheHorizon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{G}");

        // Search your library for up to three basic land cards, reveal them, and put them into your hand. Then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(0, 3, StaticFilters.FILTER_CARD_BASIC_LANDS), true));
    }

    private SeekTheHorizon(final SeekTheHorizon card) {
        super(card);
    }

    @Override
    public SeekTheHorizon copy() {
        return new SeekTheHorizon(this);
    }
}
