package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;

/**
 *
 * @author muz
 */
public final class DefinitelyNotATurtle extends CardImpl {

    private static final FilterCard filter = new FilterCard("a land or legendary Turtle card");

    static {
        filter.add(Predicates.or(
            CardType.LAND.getPredicate(),
            Predicates.and(
                SuperType.LEGENDARY.getPredicate(),
                SubType.TURTLE.getPredicate()
            )
        ));
    }

    public DefinitelyNotATurtle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}");

        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.NINJA);
        this.subtype.add(SubType.TURTLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When this creature dies, look at the top six cards of your library. You may reveal a land or legendary Turtle card from among them and put it into your hand. Put the rest on the bottom of your library in a random order.
        this.addAbility(new DiesSourceTriggeredAbility(new LookLibraryAndPickControllerEffect(
            6, 1, filter, PutCards.HAND, PutCards.BOTTOM_RANDOM
        )));

    }

    private DefinitelyNotATurtle(final DefinitelyNotATurtle card) {
        super(card);
    }

    @Override
    public DefinitelyNotATurtle copy() {
        return new DefinitelyNotATurtle(this);
    }
}
