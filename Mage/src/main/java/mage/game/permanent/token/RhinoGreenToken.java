package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author mschatz
 */
public final class RhinoGreenToken extends TokenImpl {

    public RhinoGreenToken(int power_val, int toughness_val) {
        super("RhinoGreen", power_val + "/" + toughness_val + " green Rhino creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.RHINO );
        power = new MageInt(power_val);
        toughness = new MageInt(toughness_val);
    }

    public RhinoGreenToken() {
        this(2, 2);
    }

    public RhinoGreenToken(final RhinoGreenToken token) {
        super(token);
    }

    public RhinoGreenToken copy() {
        return new RhinoGreenToken(this);
    }
}
