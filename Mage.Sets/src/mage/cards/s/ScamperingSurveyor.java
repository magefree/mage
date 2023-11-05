package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
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
 * @author xenohedron
 */
public final class ScamperingSurveyor extends CardImpl {

    private static final FilterCard filter = new FilterCard("a basic land card or Cave card");

    static {
        filter.add(Predicates.or(
                Predicates.and(
                        SuperType.BASIC.getPredicate(),
                        CardType.LAND.getPredicate()
                ),
                SubType.CAVE.getPredicate()
        ));
    }

    public ScamperingSurveyor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");
        
        this.subtype.add(SubType.GNOME);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Scampering Surveyor enters the battlefield, search your library for a basic land card or Cave card, put it onto the battlefield tapped, then shuffle.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter), true)));

    }

    private ScamperingSurveyor(final ScamperingSurveyor card) {
        super(card);
    }

    @Override
    public ScamperingSurveyor copy() {
        return new ScamperingSurveyor(this);
    }
}
