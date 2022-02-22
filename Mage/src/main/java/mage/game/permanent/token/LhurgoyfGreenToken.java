package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author mschatz
 */
public final class LhurgoyfGreenToken extends TokenImpl {

    public LhurgoyfGreenToken(int power_val, int toughness_val) {
        super("LhurgoyfGreen", power_val + "/" + toughness_val + " green Lhurgoyf creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.LHURGOYF );
        power = new MageInt(power_val);
        toughness = new MageInt(toughness_val);
    }

    public LhurgoyfGreenToken() {
        this(1, 1);
    }

    public LhurgoyfGreenToken(final LhurgoyfGreenToken token) {
        super(token);
    }

    public LhurgoyfGreenToken copy() {
        return new LhurgoyfGreenToken(this);
    }
}
