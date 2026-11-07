package mage.cards.f;

import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.BeamMeUpAbility;
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
 * @author muz
 */
public final class FinalFrontier extends CardImpl {

    private static final FilterCard filter = new FilterCard("basic land card or Planet card");

    static {
        filter.add(Predicates.or(
            Predicates.and(
                SuperType.BASIC.getPredicate(),
                CardType.LAND.getPredicate()
            ),
            SubType.PLANET.getPredicate()
        ));
    }

    public FinalFrontier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Search your library for a basic land card or Planet card, put it onto the battlefield tapped, then shuffle.
        this.getSpellAbility().addEffect(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter), true));

        // Beam me up {1}{G}
        this.addAbility(new BeamMeUpAbility(this, "{1}{G}"));
    }

    private FinalFrontier(final FinalFrontier card) {
        super(card);
    }

    @Override
    public FinalFrontier copy() {
        return new FinalFrontier(this);
    }
}
