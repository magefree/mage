

package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author Backfir3
 */
public final class CateranSummons extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("Mercenary card");

    static {
        filter.add(SubType.MERCENARY.getPredicate());
    }

    public CateranSummons(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{B}");

        // Search your library for a Mercenary card, reveal that card, and put it into your hand. Then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true, true));
    }

    private CateranSummons(final CateranSummons card) {
        super(card);
    }

    @Override
    public CateranSummons copy() {
        return new CateranSummons(this);
    }
}
