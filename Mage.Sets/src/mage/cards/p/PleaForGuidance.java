
package mage.cards.p;

import java.util.UUID;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */
public final class PleaForGuidance extends CardImpl {

    private static final FilterCard filter = new FilterCard("enchantment cards");
    static {
        filter.add(new CardTypePredicate(CardType.ENCHANTMENT));
    }
    
    public PleaForGuidance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{5}{W}");


        // Search your library for up to two enchantment cards, reveal them, and put them into your hand. Then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(0,2, filter), true, true));
    }

    public PleaForGuidance(final PleaForGuidance card) {
        super(card);
    }

    @Override
    public PleaForGuidance copy() {
        return new PleaForGuidance(this);
    }
}
