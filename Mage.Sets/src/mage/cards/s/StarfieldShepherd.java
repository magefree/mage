package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.WarpAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StarfieldShepherd extends CardImpl {

    private static final FilterCard filter = new FilterCard("a basic Plains card or a creature card with mana value 1 or less");

    static {
        filter.add(Predicates.or(
                Predicates.and(
                        SuperType.BASIC.getPredicate(),
                        SubType.PLAINS.getPredicate()
                ),
                Predicates.and(
                        CardType.CREATURE.getPredicate(),
                        new ManaValuePredicate(ComparisonType.FEWER_THAN, 2)
                )
        ));
    }

    public StarfieldShepherd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");

        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When this creature enters, search your library for a basic Plains card or a creature card with mana value 1 or less, reveal it, put it into your hand, then shuffle.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true)
        ));

        // Warp {1}{W}
        this.addAbility(new WarpAbility(this, "{1}{W}"));
    }

    private StarfieldShepherd(final StarfieldShepherd card) {
        super(card);
    }

    @Override
    public StarfieldShepherd copy() {
        return new StarfieldShepherd(this);
    }
}
