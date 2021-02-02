
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterLandCard;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author Loki
 */
public final class SylvanScrying extends CardImpl {

    private static final FilterLandCard filter = new FilterLandCard("land card");

    public SylvanScrying(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{G}");


        // Search your library for a land card, reveal it, and put it into your hand. Then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true));
    }

    private SylvanScrying(final SylvanScrying card) {
        super(card);
    }

    @Override
    public SylvanScrying copy() {
        return new SylvanScrying(this);
    }
}
