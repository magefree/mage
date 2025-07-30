package mage.cards.g;

import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author nantuko
 */
public final class GarrukTheVeilCursed extends CardImpl {

    public GarrukTheVeilCursed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{}, "");
        this.nightCard = true;
        TransformingDoubleFacedCard.copyToBackFace(new mage.cards.g.GarrukRelentless(ownerId, setInfo), this);
    }

    private GarrukTheVeilCursed(final GarrukTheVeilCursed card) {
        super(card);
    }

    @Override
    public GarrukTheVeilCursed copy() {
        return new GarrukTheVeilCursed(this);
    }
}
