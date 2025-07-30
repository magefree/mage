package mage.cards.j;

import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class JaceTelepathUnbound extends CardImpl {

    public JaceTelepathUnbound(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{}, "");
        this.nightCard = true;
        TransformingDoubleFacedCard.copyToBackFace(new mage.cards.j.JaceVrynsProdigy(ownerId, setInfo), this);
    }

    private JaceTelepathUnbound(final JaceTelepathUnbound card) {
        super(card);
    }

    @Override
    public JaceTelepathUnbound copy() {
        return new JaceTelepathUnbound(this);
    }
}
