

package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 *
 * @author Quercitron
 */
public final class CitizenToken extends TokenImpl {
    
    public CitizenToken() {
        super("Citizen Token", "1/1 white Citizen creature token");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        
        subtype.add(SubType.CITIZEN);
        power = new MageInt(1);
        toughness = new MageInt(1);

        availableImageSetCodes = Arrays.asList("UMA");
    }

    public CitizenToken(final CitizenToken token) {
        super(token);
    }

    public CitizenToken copy() {
        return new CitizenToken(this);
    }
}
