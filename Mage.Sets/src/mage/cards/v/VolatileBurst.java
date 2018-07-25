package mage.cards.v;

import java.util.UUID;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class VolatileBurst extends CardImpl {

    public VolatileBurst(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");
        

        // Choose one -
        //   Volatile Burst deals 4 damage to target creature that was dealt damage this turn.
        //   Target player who lost life this turn loses 4 life.
    }

    public VolatileBurst(final VolatileBurst card) {
        super(card);
    }

    @Override
    public VolatileBurst copy() {
        return new VolatileBurst(this);
    }
}
