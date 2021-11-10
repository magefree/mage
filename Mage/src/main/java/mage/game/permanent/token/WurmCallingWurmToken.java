
package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class WurmCallingWurmToken extends TokenImpl {

    public WurmCallingWurmToken() {
        super("Wurm Token", "X/X green Wurm creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.WURM);
        power = new MageInt(0);
        toughness = new MageInt(0);
    }

    public WurmCallingWurmToken(final WurmCallingWurmToken token) {
        super(token);
    }

    public WurmCallingWurmToken copy() {
        return new WurmCallingWurmToken(this);
    }
}
