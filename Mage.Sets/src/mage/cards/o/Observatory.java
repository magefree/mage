package mage.cards.o;

import java.util.UUID;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class Observatory extends CardImpl {

    public Observatory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        

        // Observatory enters the battlefield tapped.
        // When Observatory enters the battlefield, look at the top card of each player's library.
        // {T}: Add {U} or {W} to your mana pool.
    }

    public Observatory(final Observatory card) {
        super(card);
    }

    @Override
    public Observatory copy() {
        return new Observatory(this);
    }
}
