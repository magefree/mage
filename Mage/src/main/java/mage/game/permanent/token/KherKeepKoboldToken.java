

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

import java.util.Arrays;

/**
 *
 * @author spjspj
 */
public final class KherKeepKoboldToken extends TokenImpl {

    public KherKeepKoboldToken() {
        super("Kobolds of Kher Keep", "0/1 red Kobold creature token named Kobolds of Kher Keep");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.KOBOLD);
        power = new MageInt(0);
        toughness = new MageInt(1);

        availableImageSetCodes = Arrays.asList("A25", "TSR", "CLB", "DMC", "ONC");
    }
    public KherKeepKoboldToken(final KherKeepKoboldToken token) {
        super(token);
    }

    public KherKeepKoboldToken copy() {
        return new KherKeepKoboldToken(this);
    }
}
