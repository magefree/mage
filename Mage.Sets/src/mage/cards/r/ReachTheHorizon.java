package mage.cards.r;

import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardWithDifferentNameInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ReachTheHorizon extends CardImpl {

    private static final FilterCard filter = new FilterCard("basic land cards and/or Town cards with different names");

    static {
        filter.add(Predicates.or(
                Predicates.and(
                        SuperType.BASIC.getPredicate(),
                        CardType.LAND.getPredicate()
                ),
                SubType.TOWN.getPredicate()
        ));
    }

    public ReachTheHorizon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}");

        // Search your library for up to two basic land cards and/or Town cards with different names, put them onto the battlefield tapped, then shuffle.
        this.getSpellAbility().addEffect(new SearchLibraryPutInPlayEffect(
                new TargetCardWithDifferentNameInLibrary(0, 2, filter), true
        ));
    }

    private ReachTheHorizon(final ReachTheHorizon card) {
        super(card);
    }

    @Override
    public ReachTheHorizon copy() {
        return new ReachTheHorizon(this);
    }
}
