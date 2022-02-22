package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author mschatz
 */
public final class HarpyGreenToken extends TokenImpl {

    public HarpyGreenToken(int power_val, int toughness_val) {
        super("HarpyGreen", power_val + "/" + toughness_val + " green Harpy creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.HARPY );
        power = new MageInt(power_val);
        toughness = new MageInt(toughness_val);
    }

    public HarpyGreenToken() {
        this(2, 2);
    }

    public HarpyGreenToken(final HarpyGreenToken token) {
        super(token);
    }

    public HarpyGreenToken copy() {
        return new HarpyGreenToken(this);
    }
}
