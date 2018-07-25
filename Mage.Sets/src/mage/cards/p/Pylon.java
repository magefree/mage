package mage.cards.p;

import java.util.UUID;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class Pylon extends CardImpl {

    public Pylon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        

        // Pylon enters the battlefield tapped.
        // {1}, {T}: Add {W}{U} to your mana pool.
    }

    public Pylon(final Pylon card) {
        super(card);
    }

    @Override
    public Pylon copy() {
        return new Pylon(this);
    }
}
