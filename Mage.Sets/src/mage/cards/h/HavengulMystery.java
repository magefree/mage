package mage.cards.h;

import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HavengulMystery extends CardImpl {

    public HavengulMystery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{}, "");
        this.nightCard = true;
        TransformingDoubleFacedCard.copyToBackFace(new mage.cards.h.HavengulLaboratory(ownerId, setInfo), this);
    }

    private HavengulMystery(final HavengulMystery card) {
        super(card);
    }

    @Override
    public HavengulMystery copy() {
        return new HavengulMystery(this);
    }
}
