
package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 *
 * @author spjspj
 */
public final class HellionToken extends TokenImpl {

    public HellionToken() {
        super("Hellion Token", "4/4 red Hellion creature token");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.HELLION);
        power = new MageInt(4);
        toughness = new MageInt(4);

        availableImageSetCodes = Arrays.asList("ROE", "DDP", "PCA");
    }

    public HellionToken(final HellionToken token) {
        super(token);
    }

    public HellionToken copy() {
        return new HellionToken(this);
    }
}
