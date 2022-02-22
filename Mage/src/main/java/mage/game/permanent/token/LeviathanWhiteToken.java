package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author mschatz
 */
public final class LeviathanWhiteToken extends TokenImpl {

    public LeviathanWhiteToken(int power_val, int toughness_val) {
        super("LeviathanWhite", power_val + "/" + toughness_val + " white Leviathan creature token");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.LEVIATHAN );
        power = new MageInt(power_val);
        toughness = new MageInt(toughness_val);
    }

    public LeviathanWhiteToken() {
        this(2, 2);
    }

    public LeviathanWhiteToken(final LeviathanWhiteToken token) {
        super(token);
    }

    public LeviathanWhiteToken copy() {
        return new LeviathanWhiteToken(this);
    }
}
