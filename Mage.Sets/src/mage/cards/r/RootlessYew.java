package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.filter.predicate.mageobject.ToughnessPredicate;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RootlessYew extends CardImpl {

    private static final FilterCard filter
            = new FilterCreatureCard("creature card with power or toughness 6 or greater");

    static {
        filter.add(Predicates.or(
                new PowerPredicate(ComparisonType.MORE_THAN, 5),
                new ToughnessPredicate(ComparisonType.MORE_THAN, 5)
        ));
    }

    public RootlessYew(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");

        this.subtype.add(SubType.TREEFOLK);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // When Rootless Yew dies, search your library for a creature card with power or toughness 6 or greater, reveal it, put it into your hand, then shuffle your library.
        this.addAbility(new DiesSourceTriggeredAbility(
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true)
        ));
    }

    private RootlessYew(final RootlessYew card) {
        super(card);
    }

    @Override
    public RootlessYew copy() {
        return new RootlessYew(this);
    }
}
