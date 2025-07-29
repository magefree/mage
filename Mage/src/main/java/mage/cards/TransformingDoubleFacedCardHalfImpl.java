package mage.cards;

import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public class TransformingDoubleFacedCardHalfImpl extends CardImpl {

    TransformingDoubleFacedCardHalfImpl(UUID ownerId, CardSetInfo setInfo, String costs) {
        super(ownerId, setInfo, new CardType[]{}, costs);
    }

    private TransformingDoubleFacedCardHalfImpl(final TransformingDoubleFacedCardHalfImpl card) {
        super(card);
    }

    @Override
    public TransformingDoubleFacedCardHalfImpl copy() {
        return new TransformingDoubleFacedCardHalfImpl(this);
    }
}
