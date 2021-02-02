
package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterLandCard;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author North
 */
public final class RangersPath extends CardImpl {

    private static final FilterLandCard filter = new FilterLandCard("Forest cards");

    static {
        filter.add(SubType.FOREST.getPredicate());
    }

    public RangersPath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{G}");


        // Search your library for up to two Forest cards and put them onto the battlefield tapped. Then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(0, 2, filter), true));
    }

    private RangersPath(final RangersPath card) {
        super(card);
    }

    @Override
    public RangersPath copy() {
        return new RangersPath(this);
    }
}
