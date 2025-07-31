package mage.cards.b;

import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BladewheelChariot extends CardImpl {

    public BladewheelChariot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "");
        this.nightCard = true;
        TransformingDoubleFacedCard.copyToBackFace(new mage.cards.s.SpringLoadedSawblades(ownerId, setInfo), this);
    }

    private BladewheelChariot(final BladewheelChariot card) {
        super(card);
    }

    @Override
    public BladewheelChariot copy() {
        return new BladewheelChariot(this);
    }
}
