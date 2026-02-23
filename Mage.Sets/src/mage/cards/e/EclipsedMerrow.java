package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EclipsedMerrow extends CardImpl {

    private static final FilterCard filter = new FilterCard("Merfolk, Plains, or Island card");

    static {
        filter.add(Predicates.or(
                SubType.MERFOLK.getPredicate(),
                SubType.PLAINS.getPredicate(),
                SubType.ISLAND.getPredicate()
        ));
    }

    public EclipsedMerrow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W/U}{W/U}{W/U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When this creature enters, look at the top four cards of your library. You may reveal a Merfolk, Plains, or Island card from among them and put it into your hand. Put the rest on the bottom of your library in a random order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new LookLibraryAndPickControllerEffect(
                4, 1, filter, PutCards.HAND, PutCards.BOTTOM_RANDOM
        )));
    }

    private EclipsedMerrow(final EclipsedMerrow card) {
        super(card);
    }

    @Override
    public EclipsedMerrow copy() {
        return new EclipsedMerrow(this);
    }
}
