package mage.cards.s;

import java.util.UUID;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class Stimpack extends CardImpl {

    public Stimpack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");
        

        // Target creature gets +2/+2 and gains haste until end of turn. Stimpack deals 2 damage to you.
    }

    public Stimpack(final Stimpack card) {
        super(card);
    }

    @Override
    public Stimpack copy() {
        return new Stimpack(this);
    }
}
