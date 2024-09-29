package mage.cards.m;

import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MapTheFrontier extends CardImpl {

    private static final FilterCard filter = new FilterCard("basic land cards and/or Desert cards");

    static {
        filter.add(Predicates.or(
                Predicates.and(
                        SuperType.BASIC.getPredicate(),
                        CardType.LAND.getPredicate()
                ), SubType.DESERT.getPredicate()
        ));
    }

    public MapTheFrontier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}");

        // Search your library for up to two basic land cards and/or Desert cards, put them onto the battlefield tapped, then shuffle.
        this.getSpellAbility().addEffect(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(0, 2, filter), true));
    }

    private MapTheFrontier(final MapTheFrontier card) {
        super(card);
    }

    @Override
    public MapTheFrontier copy() {
        return new MapTheFrontier(this);
    }
}
