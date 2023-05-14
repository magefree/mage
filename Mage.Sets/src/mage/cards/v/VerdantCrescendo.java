
package mage.cards.v;

import mage.abilities.effects.common.search.SearchLibraryGraveyardPutInHandEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class VerdantCrescendo extends CardImpl {

    private static final FilterCard filter = new FilterCard("Nissa, Nature's Artisan");

    static {
        filter.add(new NamePredicate("Nissa, Nature's Artisan"));
    }

    public VerdantCrescendo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}");

        // Search your library for a basic land card and put it onto the battlefield tapped.
        this.getSpellAbility().addEffect(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true)
                .setText("search your library for a basic land card and put it onto the battlefield tapped"));

        // Search your library and graveyard for a card named Nissa, Nature's Artisan, reveal it, and put it into your hand. Then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryGraveyardPutInHandEffect(filter, true));
    }

    private VerdantCrescendo(final VerdantCrescendo card) {
        super(card);
    }

    @Override
    public VerdantCrescendo copy() {
        return new VerdantCrescendo(this);
    }
}
