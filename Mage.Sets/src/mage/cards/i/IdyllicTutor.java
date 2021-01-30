package mage.cards.i;

import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.common.FilterEnchantmentCard;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author Loki
 */
public final class IdyllicTutor extends CardImpl {

    private static final FilterCard filter = new FilterEnchantmentCard("an enchantment card");

    public IdyllicTutor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{W}");

        // Search your library for an enchantment card, reveal it, and put it into your hand. Then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true));
    }

    private IdyllicTutor(final IdyllicTutor card) {
        super(card);
    }

    @Override
    public IdyllicTutor copy() {
        return new IdyllicTutor(this);
    }
}
