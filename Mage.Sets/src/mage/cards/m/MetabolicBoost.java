package mage.cards.m;

import java.util.UUID;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class MetabolicBoost extends CardImpl {

    public MetabolicBoost(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}");
        

        // Target creature gets +1/+1 until end of turn for each creature you control.
    }

    public MetabolicBoost(final MetabolicBoost card) {
        super(card);
    }

    @Override
    public MetabolicBoost copy() {
        return new MetabolicBoost(this);
    }
}
