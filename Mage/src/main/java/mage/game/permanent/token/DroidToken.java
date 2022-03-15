
package mage.game.permanent.token;

import java.util.Collections;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Styxo
 */
public final class DroidToken extends TokenImpl {

    public DroidToken() {
        super("Droid Token", "1/1 colorless Droid creature token");
        availableImageSetCodes.addAll(Collections.singletonList("SWS"));

        cardType.add(CardType.CREATURE);
        cardType.add(CardType.ARTIFACT);
        subtype.add(SubType.DROID);

        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    public DroidToken(final DroidToken token) {
        super(token);
    }

    public DroidToken copy() {
        return new DroidToken(this);
    }
}
