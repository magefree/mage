package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author mschatz
 */
public final class OrcGreenToken extends TokenImpl {

    public OrcGreenToken(int power_val, int toughness_val) {
        super("OrcGreen", power_val + "/" + toughness_val + " green Orc creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.ORC );
        power = new MageInt(power_val);
        toughness = new MageInt(toughness_val);
    }

    public OrcGreenToken() {
        this(2, 2);
    }

    public OrcGreenToken(final OrcGreenToken token) {
        super(token);
    }

    public OrcGreenToken copy() {
        return new OrcGreenToken(this);
    }
}
