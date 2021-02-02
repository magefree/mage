package mage.cards.t;

import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterLandCard;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class ThreeVisits extends CardImpl {

    private static final FilterLandCard filter = new FilterLandCard("Forest card");

    static {
        filter.add(SubType.FOREST.getPredicate());
    }

    public ThreeVisits(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        // Search your library for a Forest card and put that card onto the battlefield. Then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter)));
    }

    private ThreeVisits(final ThreeVisits card) {
        super(card);
    }

    @Override
    public ThreeVisits copy() {
        return new ThreeVisits(this);
    }
}
