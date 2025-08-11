package mage.cards.s;

import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SerraFaithkeeper extends CardImpl {

    public SerraFaithkeeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");
        this.nightCard = true;
        TransformingDoubleFacedCard.copyToBackFace(new mage.cards.i.InvasionOfDominaria(ownerId, setInfo), this);
    }

    private SerraFaithkeeper(final SerraFaithkeeper card) {
        super(card);
    }

    @Override
    public SerraFaithkeeper copy() {
        return new SerraFaithkeeper(this);
    }
}
