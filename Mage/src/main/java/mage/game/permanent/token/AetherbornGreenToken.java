package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author mschatz
 */
public final class AetherbornGreenToken extends TokenImpl {

    public AetherbornGreenToken(int power_val, int toughness_val) {
        super("AetherbornGreen", power_val + "/" + toughness_val + " green Aetherborn creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.AETHERBORN );
        power = new MageInt(power_val);
        toughness = new MageInt(toughness_val);
    }

    public AetherbornGreenToken() {
        this(2, 2);
    }

    public AetherbornGreenToken(final AetherbornGreenToken token) {
        super(token);
    }

    public AetherbornGreenToken copy() {
        return new AetherbornGreenToken(this);
    }
}
