
package mage.game.permanent.token;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.keyword.HasteAbility;

import java.util.Arrays;

/**
 *
 * @author spjspj
 */
public final class XenagosSatyrToken extends TokenImpl {

    public XenagosSatyrToken() {
        super("Satyr Token", "2/2 red and green Satyr creature token with haste");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        color.setGreen(true);
        subtype.add(SubType.SATYR);
        power = new MageInt(2);
        toughness = new MageInt(2);

        this.addAbility(HasteAbility.getInstance());

        availableImageSetCodes = Arrays.asList("THS", "CLB");
    }
    public XenagosSatyrToken(final XenagosSatyrToken token) {
        super(token);
    }

    public XenagosSatyrToken copy() {
        return new XenagosSatyrToken(this);
    }
}
