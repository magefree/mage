package mage.cards.u;

import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.cards.c.CloisteredYouth;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author Loki
 */
public final class UnholyFiend extends CardImpl {

    public UnholyFiend(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{}, "");
        this.nightCard = true;
        TransformingDoubleFacedCard.copyToBackFace(new CloisteredYouth(ownerId, setInfo), this);
    }

    private UnholyFiend(final UnholyFiend card) {
        super(card);
    }

    @Override
    public UnholyFiend copy() {
        return new UnholyFiend(this);
    }
}
