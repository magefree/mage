package mage.cards.o;

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
import mage.filter.predicate.card.DoubleFacedCardPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OvergrownPest extends CardImpl {

    private static final FilterCard filter = new FilterCard("a land or double-faced card");

    static {
        filter.add(Predicates.or(
                CardType.LAND.getPredicate(),
                DoubleFacedCardPredicate.instance
        ));
    }

    public OvergrownPest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.PEST);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Overgrown Pest enters the battlefield, look at the top five cards of your library. You may reveal a land or double-faced card from among them and put that card into your hand. Put the rest on the bottom of your library in a random order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new LookLibraryAndPickControllerEffect(
                5, 1, filter, PutCards.HAND, PutCards.BOTTOM_RANDOM
        )));
    }

    private OvergrownPest(final OvergrownPest card) {
        super(card);
    }

    @Override
    public OvergrownPest copy() {
        return new OvergrownPest(this);
    }
}
