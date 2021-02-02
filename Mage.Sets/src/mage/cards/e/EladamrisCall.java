
package mage.cards.e;

import java.util.UUID;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */
public final class EladamrisCall extends CardImpl {

    private static final FilterCard filter = new FilterCard("creature card");
    
    static {
        filter.add(CardType.CREATURE.getPredicate());
    }
    
    public EladamrisCall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{G}{W}");

        // Search your library for a creature card, reveal that card, and put it into your hand. Then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true, true));
    }

    private EladamrisCall(final EladamrisCall card) {
        super(card);
    }

    @Override
    public EladamrisCall copy() {
        return new EladamrisCall(this);
    }
}
