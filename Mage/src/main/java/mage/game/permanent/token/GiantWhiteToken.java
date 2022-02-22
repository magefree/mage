package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author mschatz
 */
public final class GiantWhiteToken extends TokenImpl {

    public GiantWhiteToken(int power_val, int toughness_val) {
        super("GiantWhite", power_val + "/" + toughness_val + " white Giant creature token");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.GIANT );
        power = new MageInt(power_val);
        toughness = new MageInt(toughness_val);
    }

    public GiantWhiteToken() {
        this(2, 2);
    }

    public GiantWhiteToken(final GiantWhiteToken token) {
        super(token);
    }

    public GiantWhiteToken copy() {
        return new GiantWhiteToken(this);
    }
}
