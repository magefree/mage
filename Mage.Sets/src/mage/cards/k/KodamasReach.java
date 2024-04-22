package mage.cards.k;

import mage.abilities.effects.common.search.SearchLibraryPutOneOntoBattlefieldTappedRestInHandEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class KodamasReach extends CardImpl {

    public KodamasReach(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");
        this.subtype.add(SubType.ARCANE);

        // Search your library for up to two basic land cards, reveal those cards, put one onto the battlefield tapped and the other into your hand, then shuffle.
        this.getSpellAbility().addEffect(new SearchLibraryPutOneOntoBattlefieldTappedRestInHandEffect(
                new TargetCardInLibrary(0, 2, StaticFilters.FILTER_CARD_BASIC_LANDS)));
    }

    private KodamasReach(final KodamasReach card) {
        super(card);
    }

    @Override
    public KodamasReach copy() {
        return new KodamasReach(this);
    }
}
