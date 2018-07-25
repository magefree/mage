package mage.cards.m;

import java.util.UUID;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class MassRecall extends CardImpl {

    public MassRecall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{U}");
        

        // As an additional cost to cast Mass Recall, return X permanents you control to their owner's hands.
        // Draw X cards.
    }

    public MassRecall(final MassRecall card) {
        super(card);
    }

    @Override
    public MassRecall copy() {
        return new MassRecall(this);
    }
}
