
package mage.cards.b;

import java.util.UUID;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.card.CardManaCostLessThanControlledLandCountPredicate;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author Plopman
 */
public final class BeseechTheQueen extends CardImpl {

    private static final FilterCard filter = new FilterCard("card with mana value less than or equal to the number of lands you control");
    static {
        filter.add(CardManaCostLessThanControlledLandCountPredicate.getInstance());
    }

    public BeseechTheQueen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2/B}{2/B}{2/B}");


        // <i>({2B} can be paid with any two mana or with {B}. This card's converted mana cost is 6.)</i>
        // Search your library for a card with converted mana cost less than or equal to the number of lands you control, reveal it, and put it into your hand. Then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true, true));
    }

    private BeseechTheQueen(final BeseechTheQueen card) {
        super(card);
    }

    @Override
    public BeseechTheQueen copy() {
        return new BeseechTheQueen(this);
    }
}
