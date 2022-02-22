package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author mschatz
 */
public final class KavuGreenToken extends TokenImpl {

    public KavuGreenToken(int power_val, int toughness_val) {
        super("KavuGreen", power_val + "/" + toughness_val + " green Kavu creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.KAVU );
        power = new MageInt(power_val);
        toughness = new MageInt(toughness_val);
    }

    public KavuGreenToken() {
        this(2, 2);
    }

    public KavuGreenToken(final KavuGreenToken token) {
        super(token);
    }

    public KavuGreenToken copy() {
        return new KavuGreenToken(this);
    }
}
