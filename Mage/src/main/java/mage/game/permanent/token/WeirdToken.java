

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.FlyingAbility;

import java.util.Arrays;

/**
 *
 * @author spjspj
 */
public final class WeirdToken extends TokenImpl {

    public WeirdToken() {
        super("Weird Token", "3/3 blue Weird create token with defender and flying");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add(SubType.WEIRD);
        power = new MageInt(3);
        toughness = new MageInt(3);
        this.addAbility(DefenderAbility.getInstance());
        this.addAbility(FlyingAbility.getInstance());

        availableImageSetCodes = Arrays.asList("GK1");
    }

    public WeirdToken(final WeirdToken token) {
        super(token);
    }

    public WeirdToken copy() {
        return new WeirdToken(this);
    }
}
