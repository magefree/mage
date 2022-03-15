
package mage.game.permanent.token;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

/**
 *
 * @author spjspj
 */
public final class KalitasVampireToken extends TokenImpl {

    public KalitasVampireToken() {
        this(1,1);
    }

    public KalitasVampireToken(int tokenPower, int tokenToughness) {
        super("Vampire Token", new StringBuilder(tokenPower).append('/').append(tokenToughness).append(" black Vampire creature token").toString());
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.VAMPIRE);
        power = new MageInt(tokenPower);
        toughness = new MageInt(tokenToughness);
    }

    public KalitasVampireToken(final KalitasVampireToken token) {
        super(token);
    }

    public KalitasVampireToken copy() {
        return new KalitasVampireToken(this);
    }
}
