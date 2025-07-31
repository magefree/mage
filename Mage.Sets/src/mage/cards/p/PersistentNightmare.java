package mage.cards.p;

import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class PersistentNightmare extends CardImpl {

    public PersistentNightmare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");
        this.nightCard = true;
        TransformingDoubleFacedCard.copyToBackFace(new mage.cards.s.StartledAwake(ownerId, setInfo), this);
    }

    private PersistentNightmare(final PersistentNightmare card) {
        super(card);
    }

    @Override
    public PersistentNightmare copy() {
        return new PersistentNightmare(this);
    }
}
