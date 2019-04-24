
package mage.game.permanent.token;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.keyword.HasteAbility;

/**
 *
 * @author spjspj
 */
public final class RevelOfTheFallenGodSatyrToken extends TokenImpl {

    public RevelOfTheFallenGodSatyrToken() {
        super("Satyr", "2/2 red and green Satyr creature tokens with haste");
        this.setOriginalExpansionSetCode("THS");
        cardType.add(CardType.CREATURE);
        color.setColor(ObjectColor.RED);
        color.setColor(ObjectColor.GREEN);
        subtype.add(SubType.SATYR);
        power = new MageInt(2);
        toughness = new MageInt(2);
        addAbility(HasteAbility.getInstance());
    }

    public RevelOfTheFallenGodSatyrToken(final RevelOfTheFallenGodSatyrToken token) {
        super(token);
    }

    public RevelOfTheFallenGodSatyrToken copy() {
        return new RevelOfTheFallenGodSatyrToken(this);
    }
}
