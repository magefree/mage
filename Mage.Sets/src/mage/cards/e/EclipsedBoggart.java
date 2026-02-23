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
public final class EclipsedBoggart extends CardImpl {

    private static final FilterCard filter = new FilterCard("Goblin, Swamp, or Mountain card");

    static {
        filter.add(Predicates.or(
                SubType.GOBLIN.getPredicate(),
                SubType.SWAMP.getPredicate(),
                SubType.MOUNTAIN.getPredicate()
        ));
    }

    public EclipsedBoggart(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B/R}{B/R}{B/R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When this creature enters, look at the top four cards of your library. You may reveal a Goblin, Swamp, or Mountain card from among them and put it into your hand. Put the rest on the bottom of your library in a random order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new LookLibraryAndPickControllerEffect(
                4, 1, filter, PutCards.HAND, PutCards.BOTTOM_RANDOM
        )));
    }

    private EclipsedBoggart(final EclipsedBoggart card) {
        super(card);
    }

    @Override
    public EclipsedBoggart copy() {
        return new EclipsedBoggart(this);
    }
}
