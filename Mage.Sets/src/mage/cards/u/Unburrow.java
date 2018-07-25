package mage.cards.u;

import java.util.UUID;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class Unburrow extends CardImpl {

    public Unburrow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}{G}");
        

        // Choose one -
        //   Return target creature card from your graveyard to your hand.
        //   Put two 1/1 green Zerg creature tokens onto the battlefield.
        //   Creatures you control get +1/+1 until end of turn.
    }

    public Unburrow(final Unburrow card) {
        super(card);
    }

    @Override
    public Unburrow copy() {
        return new Unburrow(this);
    }
}
