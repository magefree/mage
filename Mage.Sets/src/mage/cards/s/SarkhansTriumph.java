
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author fireshoes
 */
public final class SarkhansTriumph extends CardImpl {
    
    private static final FilterCreatureCard filter = new FilterCreatureCard("Dragon creature card");
    
    static {
        filter.add(new SubtypePredicate(SubType.DRAGON));
    }

    public SarkhansTriumph(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{R}");

        // Search your library for a Dragon creature card, reveal it, put it into your hand, then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true, true));
    }

    public SarkhansTriumph(final SarkhansTriumph card) {
        super(card);
    }

    @Override
    public SarkhansTriumph copy() {
        return new SarkhansTriumph(this);
    }
}
