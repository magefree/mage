package mage.cards.o;

import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class OrmendahlProfanePrince extends CardImpl {

    public OrmendahlProfanePrince(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{}, "");
        this.nightCard = true;
        TransformingDoubleFacedCard.copyToBackFace(new mage.cards.w.WestvaleAbbey(ownerId, setInfo), this);
    }

    private OrmendahlProfanePrince(final OrmendahlProfanePrince card) {
        super(card);
    }

    @Override
    public OrmendahlProfanePrince copy() {
        return new OrmendahlProfanePrince(this);
    }
}
