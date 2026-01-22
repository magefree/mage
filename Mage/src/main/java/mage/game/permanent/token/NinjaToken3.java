package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class NinjaToken3 extends TokenImpl {

    public NinjaToken3() {
        super("Ninja Token", "1/1 black Ninja creature token");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.NINJA);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    private NinjaToken3(final NinjaToken3 token) {
        super(token);
    }

    public NinjaToken3 copy() {
        return new NinjaToken3(this);
    }
}
