package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BoromirGondorsHope extends CardImpl {

    private static final FilterCard filter = new FilterCard("a Human or artifact card");

    static {
        filter.add(Predicates.or(
                SubType.HUMAN.getPredicate(),
                CardType.ARTIFACT.getPredicate()
        ));
    }

    public BoromirGondorsHope(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Whenever Boromir, Gondor's Hope enters the battlefield or attacks, look at the top six cards of your library. You may reveal a Human or artifact card from among them and put it into your hand. Put the rest on the bottom of your library in a random order.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(new LookLibraryAndPickControllerEffect(
                6, 1, filter, PutCards.HAND, PutCards.BOTTOM_RANDOM
        )));
    }

    private BoromirGondorsHope(final BoromirGondorsHope card) {
        super(card);
    }

    @Override
    public BoromirGondorsHope copy() {
        return new BoromirGondorsHope(this);
    }
}
