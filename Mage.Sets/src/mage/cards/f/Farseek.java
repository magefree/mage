
package mage.cards.f;

import java.util.UUID;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author Loki
 */
public final class Farseek extends CardImpl {

    private static final FilterCard filter = new FilterCard("Plains, Island, Swamp, or Mountain card");

    static {
        filter.add(Predicates.or(
                SubType.PLAINS.getPredicate(),
                SubType.ISLAND.getPredicate(),
                SubType.SWAMP.getPredicate(),
                SubType.MOUNTAIN.getPredicate()));
    }

    public Farseek(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{G}");


        // Search your library for a Plains, Island, Swamp, or Mountain card and put it onto the battlefield tapped. Then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter), true));
    }

    private Farseek(final Farseek card) {
        super(card);
    }

    @Override
    public Farseek copy() {
        return new Farseek(this);
    }
}
