package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author mschatz
 */
public final class JuggernautGreenToken extends TokenImpl {

    public JuggernautGreenToken(int power_val, int toughness_val) {
        super("JuggernautGreen", power_val + "/" + toughness_val + " green Juggernaut creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.JUGGERNAUT);
        power = new MageInt(power_val);
        toughness = new MageInt(toughness_val);
    }

    public JuggernautGreenToken() {
        this(2, 2);
    }

    public JuggernautGreenToken(final JuggernautGreenToken token) {
        super(token);
    }

    public JuggernautGreenToken copy() {
        return new JuggernautGreenToken(this);
    }
}
