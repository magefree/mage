package mage.cards.w;

import mage.abilities.effects.common.search.SearchLibraryWithLessCMCPutInPlayEffect;
import mage.abilities.keyword.ImproviseAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class WhirOfInvention extends CardImpl {

    public WhirOfInvention(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{U}{U}{U}");

        // Improvise <i>(Your artifacts can help cast this spell. Each artifact you tap after you're done activating mana abilities pays for {1}.)
        this.addAbility(new ImproviseAbility());

        // Search your library for an artifact card with converted mana cost X or less, put it onto the battlefield, then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryWithLessCMCPutInPlayEffect(StaticFilters.FILTER_CARD_ARTIFACT));
    }

    private WhirOfInvention(final WhirOfInvention card) {
        super(card);
    }

    @Override
    public WhirOfInvention copy() {
        return new WhirOfInvention(this);
    }
}
