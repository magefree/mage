

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.constants.SuperType;

import java.util.Arrays;

/**
 *
 * @author spjspj
 */
public final class VojaToken extends TokenImpl {

    public VojaToken() {
        super("Voja", "Voja, a legendary 2/2 green and white Wolf creature token");
        this.cardType.add(CardType.CREATURE);
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.WOLF);

        this.color.setGreen(true);
        this.color.setWhite(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        availableImageSetCodes = Arrays.asList("GK1");
    }

    public VojaToken(final VojaToken token) {
        super(token);
    }

    public VojaToken copy() {
        return new VojaToken(this);
    }

}
