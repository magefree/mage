package mage.cards.s;

import java.util.UUID;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class StrikeCannon extends CardImpl {

    public StrikeCannon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{R}{R}");
        

        // Strike Cannon deals X damage to target creature or player.
        // Salvage {3}
    }

    public StrikeCannon(final StrikeCannon card) {
        super(card);
    }

    @Override
    public StrikeCannon copy() {
        return new StrikeCannon(this);
    }
}
