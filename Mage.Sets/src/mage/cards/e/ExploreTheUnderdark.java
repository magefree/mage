package mage.cards.e;

import mage.abilities.effects.common.TakeTheInitiativeEffect;
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
public final class ExploreTheUnderdark extends CardImpl {

    private static final FilterCard filter = new FilterCard("basic land cards and/or Gate cards");

    static {
        filter.add(Predicates.or(
                Predicates.and(
                        SuperType.BASIC.getPredicate(),
                        CardType.LAND.getPredicate()
                ), SubType.GATE.getPredicate()
        ));
    }

    public ExploreTheUnderdark(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{G}");

        // Search your library for up to two basic land cards and/or Gate cards, put them onto the battlefield tapped, then shuffle.
        this.getSpellAbility().addEffect(new SearchLibraryPutInPlayEffect(
                new TargetCardInLibrary(0, 2, filter), true
        ));

        // You take the initiative.
        this.getSpellAbility().addEffect(new TakeTheInitiativeEffect().concatBy("<br>"));
    }

    private ExploreTheUnderdark(final ExploreTheUnderdark card) {
        super(card);
    }

    @Override
    public ExploreTheUnderdark copy() {
        return new ExploreTheUnderdark(this);
    }
}
