
package mage.game.permanent.token;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

/**
 *
 * @author spjspj
 */
public final class ReflectionToken extends TokenImpl {

    public ReflectionToken() {
        super("Reflection Token", "2/2 white Reflection creature token");
        this.setOriginalExpansionSetCode("TMP");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.REFLECTION);
        power = new MageInt(2);
        toughness = new MageInt(2);
    }

    public ReflectionToken(final ReflectionToken token) {
        super(token);
    }

    public ReflectionToken copy() {
        return new ReflectionToken(this);
    }
}
