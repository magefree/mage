package mage.cards.p;

import java.util.UUID;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class PsionicFeedback extends CardImpl {

    public PsionicFeedback(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");
        

        // Counter target spell unless its controller pays {3}.
    }

    public PsionicFeedback(final PsionicFeedback card) {
        super(card);
    }

    @Override
    public PsionicFeedback copy() {
        return new PsionicFeedback(this);
    }
}
