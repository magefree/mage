package mage.cards.p;

import java.util.UUID;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class PsionicStorm extends CardImpl {

    public PsionicStorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}{W}");
        

        // Each player chooses a number of creatures he or she controls equal to the number of creatures controlled by the player with the fewest, then sacrifices the rest.
    }

    public PsionicStorm(final PsionicStorm card) {
        super(card);
    }

    @Override
    public PsionicStorm copy() {
        return new PsionicStorm(this);
    }
}
