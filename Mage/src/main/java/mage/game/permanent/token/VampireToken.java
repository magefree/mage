
package mage.game.permanent.token;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;

import java.util.Arrays;

/**
 *
 * @author spjspj
 */
public final class VampireToken extends TokenImpl {

    public VampireToken() {
        super("Vampire Token", "2/2 black Vampire creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.VAMPIRE);
        power = new MageInt(2);
        toughness = new MageInt(2);
        addAbility(FlyingAbility.getInstance());

        availableImageSetCodes = Arrays.asList("ISD", "KTK");
    }

    public VampireToken(final VampireToken token) {
        super(token);
    }

    public VampireToken copy() {
        return new VampireToken(this);
    }
}
