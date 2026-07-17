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
public final class EclipsedFlamekin extends CardImpl {

    private static final FilterCard filter = new FilterCard("Elemental, Island, or Mountain card");

    static {
        filter.add(Predicates.or(
                SubType.ELEMENTAL.getPredicate(),
                SubType.ISLAND.getPredicate(),
                SubType.MOUNTAIN.getPredicate()
        ));
    }

    public EclipsedFlamekin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U/R}{U/R}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // When this creature enters, look at the top four cards of your library. You may reveal an Elemental, Island, or Mountain card from among them and put it into your hand. Put the rest on the bottom of your library in a random order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new LookLibraryAndPickControllerEffect(
                4, 1, filter, PutCards.HAND, PutCards.BOTTOM_RANDOM
        )));
    }

    private EclipsedFlamekin(final EclipsedFlamekin card) {
        super(card);
    }

    @Override
    public EclipsedFlamekin copy() {
        return new EclipsedFlamekin(this);
    }
}
