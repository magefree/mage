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
public final class EclipsedKithkin extends CardImpl {

    private static final FilterCard filter = new FilterCard("Kithkin, Forest, or Plains card");

    static {
        filter.add(Predicates.or(
                SubType.KITHKIN.getPredicate(),
                SubType.FOREST.getPredicate(),
                SubType.PLAINS.getPredicate()
        ));
    }

    public EclipsedKithkin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G/W}{G/W}");

        this.subtype.add(SubType.KITHKIN);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When this creature enters, look at the top four cards of your library. You may reveal a Kithkin, Forest, or Plains card from among them and put it into your hand. Put the rest on the bottom of your library in a random order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new LookLibraryAndPickControllerEffect(
                4, 1, filter, PutCards.HAND, PutCards.BOTTOM_RANDOM
        )));
    }

    private EclipsedKithkin(final EclipsedKithkin card) {
        super(card);
    }

    @Override
    public EclipsedKithkin copy() {
        return new EclipsedKithkin(this);
    }
}
