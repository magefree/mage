package mage.cards.w;

import java.util.UUID;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class WarpGate extends CardImpl {

    public WarpGate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}");
        

        // Psionic 3
        // At the beginning of your upkeep, remove a psi counter from Warp Gate. Then if Warp Gate has no psi counters on it, sacrifice it and put a 3/3 blue Protoss creature token onto the battlefield.
    }

    public WarpGate(final WarpGate card) {
        super(card);
    }

    @Override
    public WarpGate copy() {
        return new WarpGate(this);
    }
}
