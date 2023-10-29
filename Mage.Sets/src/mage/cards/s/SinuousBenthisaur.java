package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.AdditiveDynamicValue;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class SinuousBenthisaur extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent(SubType.CAVE, "Caves you control");

    private static final FilterCard filterCard = new FilterCard("Cave cards");

    static {
        filterCard.add(SubType.CAVE.getPredicate());
    }

    private static final DynamicValue xValue = new AdditiveDynamicValue(
            new PermanentsOnBattlefieldCount(filter, null),
            new CardsInControllerGraveyardCount(filterCard)
    );

    public SinuousBenthisaur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Sinuous Benthisaur enters the battlefield, look at the top X cards of your library, where X is the number of Caves you control plus the number of Cave cards in your graveyard. Put two of those cards into your hand and the rest on the bottom of your library in a random order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new LookLibraryAndPickControllerEffect(xValue, 2, PutCards.HAND, PutCards.BOTTOM_ANY)
        ).addHint(new ValueHint("Caves count", xValue)));
    }

    private SinuousBenthisaur(final SinuousBenthisaur card) {
        super(card);
    }

    @Override
    public SinuousBenthisaur copy() {
        return new SinuousBenthisaur(this);
    }
}