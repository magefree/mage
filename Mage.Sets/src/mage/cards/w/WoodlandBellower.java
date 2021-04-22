package mage.cards.w;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class WoodlandBellower extends CardImpl {

    private static final FilterCard filter = new FilterCard("nonlegendary green creature card with mana value 3 or less");

    static {
        filter.add(new ColorPredicate(ObjectColor.GREEN));
        filter.add(CardType.CREATURE.getPredicate());
        filter.add(Predicates.not(SuperType.LEGENDARY.getPredicate()));
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    public WoodlandBellower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // When Woodland Bellower enters the battlefield, you may search your library for a nonlegendary green creature card with converted mana cost 3 or less, put it onto the battlefield, then shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter)), true
        ));
    }

    private WoodlandBellower(final WoodlandBellower card) {
        super(card);
    }

    @Override
    public WoodlandBellower copy() {
        return new WoodlandBellower(this);
    }
}
