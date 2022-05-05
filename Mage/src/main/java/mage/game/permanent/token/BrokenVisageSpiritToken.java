
package mage.game.permanent.token;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

/**
 *
 * @author spjspj & L_J
 */
public final class BrokenVisageSpiritToken extends TokenImpl {

    public BrokenVisageSpiritToken() {
        this(0,0);
    }

    public BrokenVisageSpiritToken(int tokenPower, int tokenToughness) {
        super("Spirit Token", new StringBuilder(tokenPower).append('/').append(tokenToughness).append(" black Spirit creature token").toString());
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.SPIRIT);
        power = new MageInt(tokenPower);
        toughness = new MageInt(tokenToughness);
    }

    public BrokenVisageSpiritToken(final BrokenVisageSpiritToken token) {
        super(token);
    }

    public BrokenVisageSpiritToken copy() {
        return new BrokenVisageSpiritToken(this);
    }
}
