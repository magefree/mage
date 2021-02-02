
package mage.cards.w;

import java.util.UUID;
import mage.abilities.effects.common.search.SearchLibraryWithLessCMCPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterPermanentCard;

/**
 *
 * @author Plopman
 */
public final class Wargate extends CardImpl {

    public Wargate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{G}{W}{U}");

        // Search your library for a permanent card with converted mana cost X or less, put it onto the battlefield, then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryWithLessCMCPutInPlayEffect(new FilterPermanentCard()));
    }

    private Wargate(final Wargate card) {
        super(card);
    }

    @Override
    public Wargate copy() {
        return new Wargate(this);
    }
}
