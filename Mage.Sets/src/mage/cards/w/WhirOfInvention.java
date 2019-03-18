
package mage.cards.w;

import java.util.UUID;
import mage.abilities.effects.common.search.SearchLibraryWithLessCMCPutInPlayEffect;
import mage.abilities.keyword.ImproviseAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterArtifactCard;

/**
 *
 * @author fireshoes
 */
public final class WhirOfInvention extends CardImpl {

    public WhirOfInvention(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{U}{U}{U}");

        // Improvise <i>(Your artifacts can help cast this spell. Each artifact you tap after you're done activating mana abilities pays for {1}.)
        addAbility(new ImproviseAbility());

        // Search your library for an artifact card with converted mana cost X or less, put it onto the battlefield, then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryWithLessCMCPutInPlayEffect(new FilterArtifactCard()));
    }

    public WhirOfInvention(final WhirOfInvention card) {
        super(card);
    }

    @Override
    public WhirOfInvention copy() {
        return new WhirOfInvention(this);
    }
}
