package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author mschatz
 */
public final class CephalidGreenToken extends TokenImpl {

    public CephalidGreenToken(int power_val, int toughness_val) {
        super("CephalidGreen", power_val + "/" + toughness_val + " green Cephalid creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.CEPHALID );
        power = new MageInt(power_val);
        toughness = new MageInt(toughness_val);
    }

    public CephalidGreenToken() {
        this(2, 2);
    }

    public CephalidGreenToken(final CephalidGreenToken token) {
        super(token);
    }

    public CephalidGreenToken copy() {
        return new CephalidGreenToken(this);
    }
}
