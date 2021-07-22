
package mage.cards.n;

import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public final class NissasRenewal extends CardImpl {

    public NissasRenewal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{5}{G}");

        // Search your library for up to three basic land cards, put them onto the battlefield tapped, then shuffle your library. You gain 7 life.
        this.getSpellAbility().addEffect(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(0, 3, StaticFilters.FILTER_CARD_BASIC_LANDS), true));
        this.getSpellAbility().addEffect(new GainLifeEffect(7));
    }

    private NissasRenewal(final NissasRenewal card) {
        super(card);
    }

    @Override
    public NissasRenewal copy() {
        return new NissasRenewal(this);
    }
}
