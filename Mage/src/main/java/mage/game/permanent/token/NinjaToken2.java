package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author ciaccona007
 */
public final class NinjaToken2 extends TokenImpl {

    public NinjaToken2() {
        super("Ninja Token", "2/1 blue Ninja creature token");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add(SubType.NINJA);
        power = new MageInt(2);
        toughness = new MageInt(1);
    }

    private NinjaToken2(final NinjaToken2 token) {
        super(token);
    }

    public NinjaToken2 copy() {
        return new NinjaToken2(this);
    }
}
