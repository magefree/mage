package mage.cards.n;

import java.util.UUID;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class Nexus extends CardImpl {

    public Nexus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        

        // Nexus enters the battlefield tapped unless you have five or more cards in hand.
        // {T}: Add {W} or {U} to your mana pool.
    }

    public Nexus(final Nexus card) {
        super(card);
    }

    @Override
    public Nexus copy() {
        return new Nexus(this);
    }
}
