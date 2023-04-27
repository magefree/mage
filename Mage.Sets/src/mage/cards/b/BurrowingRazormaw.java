package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BurrowingRazormaw extends CardImpl {

    public BurrowingRazormaw(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // When Burrowing Razormaw dies, mill four cards.
        this.addAbility(new DiesSourceTriggeredAbility(new MillCardsControllerEffect(4)));
    }

    private BurrowingRazormaw(final BurrowingRazormaw card) {
        super(card);
    }

    @Override
    public BurrowingRazormaw copy() {
        return new BurrowingRazormaw(this);
    }
}
