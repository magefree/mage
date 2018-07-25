package mage.cards.p;

import java.util.UUID;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class PhotonOvercharge extends CardImpl {

    public PhotonOvercharge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");
        

        // Photon Overcharge deals damage to target attacking creature equal to the number of cards in your hand.
    }

    public PhotonOvercharge(final PhotonOvercharge card) {
        super(card);
    }

    @Override
    public PhotonOvercharge copy() {
        return new PhotonOvercharge(this);
    }
}
