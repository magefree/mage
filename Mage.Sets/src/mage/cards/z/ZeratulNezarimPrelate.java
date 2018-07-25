package mage.cards.z;

import java.util.UUID;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class ZeratulNezarimPrelate extends CardImpl {

    public ZeratulNezarimPrelate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{W}{U}");
        
        this.subtype.add(SubType.ZERATUL);
    }

    public ZeratulNezarimPrelate(final ZeratulNezarimPrelate card) {
        super(card);
    }

    @Override
    public ZeratulNezarimPrelate copy() {
        return new ZeratulNezarimPrelate(this);
    }
}
