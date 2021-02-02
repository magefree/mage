
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterLandCard;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author Plopman
 */
public final class SkyshroudClaim extends CardImpl {

    private static final FilterLandCard filter = new FilterLandCard("Forest cards");
    static {
        filter.add(SubType.FOREST.getPredicate());
    }
    public SkyshroudClaim(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{G}");


        // Search your library for up to two Forest cards and put them onto the battlefield. Then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(0, 2, filter ), false));
    }

    private SkyshroudClaim(final SkyshroudClaim card) {
        super(card);
    }

    @Override
    public SkyshroudClaim copy() {
        return new SkyshroudClaim(this);
    }
}
