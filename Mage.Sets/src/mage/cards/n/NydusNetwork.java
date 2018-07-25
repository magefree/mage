package mage.cards.n;

import java.util.UUID;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class NydusNetwork extends CardImpl {

    public NydusNetwork(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        

        // Nydus Network enters the battlefield tapped.
        // When Nydus Network enters the battlefield, target creature you control can't be blocked this turn.
        // {T}: Add {B} to your mana pool.
    }

    public NydusNetwork(final NydusNetwork card) {
        super(card);
    }

    @Override
    public NydusNetwork copy() {
        return new NydusNetwork(this);
    }
}
