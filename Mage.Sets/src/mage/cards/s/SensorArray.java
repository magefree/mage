package mage.cards.s;

import java.util.UUID;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class SensorArray extends CardImpl {

    public SensorArray(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}{U}");
        

        // When Sensor Array enters the battlefield, look at target opponent's hand, then name a nonland card.
        // When that opponent casts a card with the chosen name, sacrifice Sensor Array. If you do, draw two cards.
    }

    public SensorArray(final SensorArray card) {
        super(card);
    }

    @Override
    public SensorArray copy() {
        return new SensorArray(this);
    }
}
