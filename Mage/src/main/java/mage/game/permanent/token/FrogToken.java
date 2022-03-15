

package mage.game.permanent.token;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

/**
 *
 * @author nantuko
 */
public final class FrogToken extends TokenImpl {

    public FrogToken() {
        super("Frog Token", "1/1 blue Frog creature token");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add(SubType.FROG);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }
    public FrogToken(final FrogToken token) {
        super(token);
    }

    public FrogToken copy() {
        return new FrogToken(this);
    }
}
