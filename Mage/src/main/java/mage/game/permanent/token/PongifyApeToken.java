
package mage.game.permanent.token;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

/**
 *
 * @author spjspj
 */
public final class PongifyApeToken extends TokenImpl {

    public PongifyApeToken() {
        super("Ape", "3/3 green Ape creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.APE);
        power = new MageInt(3);
        toughness = new MageInt(3);
    }

    public PongifyApeToken(final PongifyApeToken token) {
        super(token);
    }

    public PongifyApeToken copy() {
        return new PongifyApeToken(this);
    }
}
