package mage.cards.f;

import java.util.UUID;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author htrajan
 */
public final class FungalRebirth extends CardImpl {

    public FungalRebirth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");
        

        // Return target permanent card from your graveyard to your hand. If a creature died this turn, create two 1/1 green Saproling creature tokens.
    }

    private FungalRebirth(final FungalRebirth card) {
        super(card);
    }

    @Override
    public FungalRebirth copy() {
        return new FungalRebirth(this);
    }
}
