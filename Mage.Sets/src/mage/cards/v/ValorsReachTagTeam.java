package mage.cards.v;

import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.cards.i.InvasionOfKylem;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ValorsReachTagTeam extends CardImpl {

    public ValorsReachTagTeam(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{}, "");
        this.nightCard = true;
        TransformingDoubleFacedCard.copyToBackFace(new InvasionOfKylem(ownerId, setInfo), this);
    }

    private ValorsReachTagTeam(final ValorsReachTagTeam card) {
        super(card);
    }

    @Override
    public ValorsReachTagTeam copy() {
        return new ValorsReachTagTeam(this);
    }
}
