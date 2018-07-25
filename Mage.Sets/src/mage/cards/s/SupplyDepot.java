package mage.cards.s;

import java.util.UUID;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class SupplyDepot extends CardImpl {

    public SupplyDepot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        

        // Supply Depot enters the battlefield tapped.
        // {T}: Add {R} or {W} to your mana pool.
        // {3}, Sacrifice Supply Depot: Draw a card.
    }

    public SupplyDepot(final SupplyDepot card) {
        super(card);
    }

    @Override
    public SupplyDepot copy() {
        return new SupplyDepot(this);
    }
}
