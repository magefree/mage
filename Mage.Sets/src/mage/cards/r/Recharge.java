package mage.cards.r;

import java.util.UUID;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class Recharge extends CardImpl {

    public Recharge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}{U}");
        

        // Choose one -
        //   Exile target creature you control, then return it to the battlefield under its owner's control.
        //   You gain 5 life.
        //   Draw two cards, then discard a card.
    }

    public Recharge(final Recharge card) {
        super(card);
    }

    @Override
    public Recharge copy() {
        return new Recharge(this);
    }
}
