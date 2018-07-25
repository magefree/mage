package mage.cards.v;

import java.util.UUID;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class Vortex extends CardImpl {

    public Vortex(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");
        

        // Exile target nonland permanent. Return it to the battlefield under its owner's control at the beginning of the next end step.
    }

    public Vortex(final Vortex card) {
        super(card);
    }

    @Override
    public Vortex copy() {
        return new Vortex(this);
    }
}
