package mage.cards.r;

import java.util.UUID;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class RaynorRebelCommander extends CardImpl {

    public RaynorRebelCommander(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{R}{W}");
        
        this.subtype.add(SubType.RAYNOR);
    }

    public RaynorRebelCommander(final RaynorRebelCommander card) {
        super(card);
    }

    @Override
    public RaynorRebelCommander copy() {
        return new RaynorRebelCommander(this);
    }
}
