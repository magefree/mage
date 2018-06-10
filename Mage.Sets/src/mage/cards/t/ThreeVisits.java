
package mage.cards.t;

import java.util.UUID;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterLandCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author Plopman
 */
public final class ThreeVisits extends CardImpl {

    private static final FilterLandCard filter = new FilterLandCard("Forest");
    static {
        filter.add(new SubtypePredicate(SubType.FOREST));
    }
    
    public ThreeVisits(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{G}");


        // Search your library for a Forest card and put that card onto the battlefield. Then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter)));
                
    }

    public ThreeVisits(final ThreeVisits card) {
        super(card);
    }

    @Override
    public ThreeVisits copy() {
        return new ThreeVisits(this);
    }
}
