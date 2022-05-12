
package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Styxo
 */
public final class HippoToken extends TokenImpl {

    public HippoToken() {
        super("Hippo Token", "1/1 green Hippo creature token");
        
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.HIPPO);
        
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    public HippoToken(final HippoToken token) {
        super(token);
    }

    public HippoToken copy() {
        return new HippoToken(this);
    }
}
