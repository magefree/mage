package mage.cards.r;

import java.util.UUID;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class Repair extends CardImpl {

    public Repair(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{W}");
        

        // Return up to two target artifact cards from your graveyard to your hand.
    }

    public Repair(final Repair card) {
        super(card);
    }

    @Override
    public Repair copy() {
        return new Repair(this);
    }
}
