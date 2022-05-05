

package mage.game.permanent.token;
import java.util.Collections;

import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Styxo
 */
public final class EwokToken extends TokenImpl {

    public EwokToken() {
        super("Ewok Token", "1/1 green Ewok creature tokens", 1, 1);
        availableImageSetCodes.addAll(Collections.singletonList("SWS"));

        cardType.add(CardType.CREATURE);
        subtype.add(SubType.EWOK);
        color.setGreen(true);
    }

    public EwokToken(final EwokToken token) {
        super(token);
    }

    public EwokToken copy() {
        return new EwokToken(this);
    }
}
