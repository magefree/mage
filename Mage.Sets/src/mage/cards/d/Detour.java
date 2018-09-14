package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.mageobject.SupertypePredicate;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author TheElk801
 */
public final class Detour extends CardImpl {

    private static final FilterCard filter
            = new FilterCard("basic land cards and/or Gate cards");

    static {
        filter.add(Predicates.or(
                Predicates.and(
                        new CardTypePredicate(CardType.LAND),
                        new SupertypePredicate(SuperType.BASIC)
                ), new SubtypePredicate(SubType.GATE)
        ));
    }

    public Detour(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}");

        // Search your library for up to two basic lands and/or Gates and put them onto the battlefield tapped.
        this.getSpellAbility().addEffect(new SearchLibraryPutInPlayEffect(
                new TargetCardInLibrary(0, 2, filter), true
        ));
    }

    public Detour(final Detour card) {
        super(card);
    }

    @Override
    public Detour copy() {
        return new Detour(this);
    }
}
