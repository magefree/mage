

package mage.cards.t;

import java.util.UUID;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SupertypePredicate;
import mage.target.common.TargetCardInLibrary;

/**
 * @author Loki
 */
public final class TimeOfNeed extends CardImpl {

    private static final FilterCard filter = new FilterCard("legendary creature card");

    static {
        filter.add(new CardTypePredicate(CardType.CREATURE));
        filter.add(new SupertypePredicate(SuperType.LEGENDARY));
    }

    public TimeOfNeed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{G}");


        // Search your library for a legendary creature card, reveal it, and put it into your hand. Then shuffle your library.
        TargetCardInLibrary target = new TargetCardInLibrary(filter);
        this.getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(target, true));
    }

    public TimeOfNeed(final TimeOfNeed card) {
        super(card);
    }

    @Override
    public TimeOfNeed copy() {
        return new TimeOfNeed(this);
    }

}
