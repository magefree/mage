package mage.cards.s;

import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.target.common.TargetCardWithDifferentNameInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SharedSummons extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("creature cards with different names");

    public SharedSummons(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{G}{G}");

        // Search your library for up to two creature cards with different names, reveal them, put them into your hand, then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(
                new TargetCardWithDifferentNameInLibrary(0, 2, filter), true
        ));
    }

    private SharedSummons(final SharedSummons card) {
        super(card);
    }

    @Override
    public SharedSummons copy() {
        return new SharedSummons(this);
    }
}
