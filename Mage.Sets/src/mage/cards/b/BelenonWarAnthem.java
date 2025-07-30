package mage.cards.b;

import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BelenonWarAnthem extends CardImpl {

    public BelenonWarAnthem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{}, "");
        this.nightCard = true;
        TransformingDoubleFacedCard.copyToBackFace(new mage.cards.i.InvasionOfBelenon(ownerId, setInfo), this);
    }

    private BelenonWarAnthem(final BelenonWarAnthem card) {
        super(card);
    }

    @Override
    public BelenonWarAnthem copy() {
        return new BelenonWarAnthem(this);
    }
}
