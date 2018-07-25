package mage.cards.w;

import java.util.UUID;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class WarpPrism extends CardImpl {

    public WarpPrism(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");
        

        // {T}: Add {W} or {U} to your mana pool.
        // {2}{W}{U}: Warp Prism becomes a 3/3 white and blue Protoss artifact creature with flying until end of turn.
    }

    public WarpPrism(final WarpPrism card) {
        super(card);
    }

    @Override
    public WarpPrism copy() {
        return new WarpPrism(this);
    }
}
