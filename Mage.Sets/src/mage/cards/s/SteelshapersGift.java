
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author jonubuu
 */
public final class SteelshapersGift extends CardImpl {

    private static final FilterCard filter = new FilterCard("Equipment card");

    static {
        filter.add(new CardTypePredicate(CardType.ARTIFACT));
        filter.add(new SubtypePredicate(SubType.EQUIPMENT));
    }

    public SteelshapersGift(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{W}");


        // Search your library for an Equipment card, reveal that card, and put it into your hand. Then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(1, 1, filter), true));
    }

    public SteelshapersGift(final SteelshapersGift card) {
        super(card);
    }

    @Override
    public SteelshapersGift copy() {
        return new SteelshapersGift(this);
    }
}
