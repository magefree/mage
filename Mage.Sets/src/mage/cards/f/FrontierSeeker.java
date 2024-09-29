package mage.cards.f;

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
public final class FrontierSeeker extends CardImpl {

    private static final FilterCard filter = new FilterCard("a Mount creature card or a Plains card");

    static {
        filter.add(Predicates.or(
                Predicates.and(
                        SubType.MOUNT.getPredicate(),
                        CardType.CREATURE.getPredicate()
                ), SubType.PLAINS.getPredicate()
        ));
    }

    public FrontierSeeker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Frontier Seeker enters the battlefield, look at the top five cards of your library. You may reveal a Mount creature card or a Plains card from among them and put it into your hand. Put the rest on the bottom of your library in a random order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new LookLibraryAndPickControllerEffect(
                5, 1, filter, PutCards.HAND, PutCards.BOTTOM_RANDOM, true
        )));
    }

    private FrontierSeeker(final FrontierSeeker card) {
        super(card);
    }

    @Override
    public FrontierSeeker copy() {
        return new FrontierSeeker(this);
    }
}
