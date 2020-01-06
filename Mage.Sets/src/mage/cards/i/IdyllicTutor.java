
package mage.cards.i;

import java.util.UUID;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInLibrary;

/**
 * @author Loki
 */
public final class IdyllicTutor extends CardImpl {

    private static final FilterCard filter = new FilterCard("an enchantment");

    static {
        filter.add(CardType.ENCHANTMENT.getPredicate());
    }

    public IdyllicTutor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{W}");

        // Search your library for an enchantment card, reveal it, and put it into your hand. Then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true));
    }

    public IdyllicTutor(final IdyllicTutor card) {
        super(card);
    }

    @Override
    public IdyllicTutor copy() {
        return new IdyllicTutor(this);
    }
}
