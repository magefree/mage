package mage.cards.h;

import java.util.UUID;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class HELIOSOne extends CardImpl {

    public HELIOSOne(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        

        // {T}: Add {C}.
        // {1}, {T}: You get {E}.
        // {3}, {T}, Pay X {E}, Sacrifice HELIOS One: Destroy target nonland permanent with mana value X. Activate only as a sorcery.
    }

    private HELIOSOne(final HELIOSOne card) {
        super(card);
    }

    @Override
    public HELIOSOne copy() {
        return new HELIOSOne(this);
    }
}
