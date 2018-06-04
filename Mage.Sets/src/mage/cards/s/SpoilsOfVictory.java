
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */
public final class SpoilsOfVictory extends CardImpl {

    private static final FilterCard filter = new FilterCard("Plains, Island, Swamp, Mountain, or Forest card");
    static {
        filter.add(Predicates.or(
                new SubtypePredicate(SubType.PLAINS),
                new SubtypePredicate(SubType.ISLAND),
                new SubtypePredicate(SubType.SWAMP),
                new SubtypePredicate(SubType.MOUNTAIN),
                new SubtypePredicate(SubType.FOREST)));
    }

    public SpoilsOfVictory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{G}");


        // Search your library for a Plains, Island, Swamp, Mountain, or Forest card and put that card onto the battlefield. Then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter), false, Outcome.PutLandInPlay));
    }

    public SpoilsOfVictory(final SpoilsOfVictory card) {
        super(card);
    }

    @Override
    public SpoilsOfVictory copy() {
        return new SpoilsOfVictory(this);
    }
}
