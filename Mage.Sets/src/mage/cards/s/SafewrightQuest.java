
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author North
 */
public final class SafewrightQuest extends CardImpl {

    private static final FilterCard filter = new FilterCard("Forest or Plains card");

    static {
        filter.add(Predicates.or(new SubtypePredicate(SubType.FOREST), new SubtypePredicate(SubType.PLAINS)));
    }

    public SafewrightQuest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{G/W}");


        // Search your library for a Forest or Plains card, reveal it, and put it into your hand. Then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true));
    }

    public SafewrightQuest(final SafewrightQuest card) {
        super(card);
    }

    @Override
    public SafewrightQuest copy() {
        return new SafewrightQuest(this);
    }
}
