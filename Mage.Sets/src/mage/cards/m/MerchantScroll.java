
package mage.cards.m;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author North
 */
public final class MerchantScroll extends CardImpl {

    private static final FilterCard filter = new FilterCard("blue instant card");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
        filter.add(CardType.INSTANT.getPredicate());
    }

    public MerchantScroll(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{U}");


        // Search your library for a blue instant card, reveal that card, and put it into your hand. Then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(1, 1, filter), true, true));
    }

    private MerchantScroll(final MerchantScroll card) {
        super(card);
    }

    @Override
    public MerchantScroll copy() {
        return new MerchantScroll(this);
    }
}
