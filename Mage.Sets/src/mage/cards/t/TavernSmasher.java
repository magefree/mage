package mage.cards.t;

import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TavernSmasher extends CardImpl {

    public TavernSmasher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");
        this.nightCard = true;
        TransformingDoubleFacedCard.copyToBackFace(new mage.cards.t.TavernRuffian(ownerId, setInfo), this);
    }

    private TavernSmasher(final TavernSmasher card) {
        super(card);
    }

    @Override
    public TavernSmasher copy() {
        return new TavernSmasher(this);
    }
}
