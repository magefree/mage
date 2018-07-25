package mage.cards.l;

import java.util.UUID;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class Liftoff extends CardImpl {

    public Liftoff(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");
        

        // Return target permanent you control to its owner's hand.
        // Draw a card.
    }

    public Liftoff(final Liftoff card) {
        super(card);
    }

    @Override
    public Liftoff copy() {
        return new Liftoff(this);
    }
}
