
package mage.cards.t;

import java.util.UUID;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author North
 */
public final class TrapmakersSnare extends CardImpl {

    private static final FilterCard filter = new FilterCard("Trap card");

    static {
        filter.add(SubType.TRAP.getPredicate());
    }

    public TrapmakersSnare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}");


        // Search your library for a Trap card, reveal it, and put it into your hand. Then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true));
    }

    private TrapmakersSnare(final TrapmakersSnare card) {
        super(card);
    }

    @Override
    public TrapmakersSnare copy() {
        return new TrapmakersSnare(this);
    }
}
