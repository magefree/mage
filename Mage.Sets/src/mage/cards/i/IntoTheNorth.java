
package mage.cards.i;

import java.util.UUID;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.filter.common.FilterLandCard;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */
public final class IntoTheNorth extends CardImpl {
    
    private static final FilterLandCard filter = new FilterLandCard("snow land card");

    static {
        filter.add(SuperType.SNOW.getPredicate());
    }
    
    public IntoTheNorth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{G}");


        // Search your library for a snow land card and put it onto the battlefield tapped. Then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter), true));
        
    }

    private IntoTheNorth(final IntoTheNorth card) {
        super(card);
    }

    @Override
    public IntoTheNorth copy() {
        return new IntoTheNorth(this);
    }
}
