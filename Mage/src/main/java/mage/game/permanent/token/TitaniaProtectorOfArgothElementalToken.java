package mage.game.permanent.token;

import mage.MageInt;
import mage.ObjectColor;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author spjspj
 */
public final class TitaniaProtectorOfArgothElementalToken extends TokenImpl {

    public TitaniaProtectorOfArgothElementalToken() {
        super("Elemental Token", "5/3 green Elemental creature token");
        this.cardType.add(CardType.CREATURE);
        this.color.setGreen(true);
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        availableImageSetCodes = Arrays.asList("C14", "CMA", "MH2");
    }

    public TitaniaProtectorOfArgothElementalToken(final TitaniaProtectorOfArgothElementalToken token) {
        super(token);
    }

    public TitaniaProtectorOfArgothElementalToken copy() {
        return new TitaniaProtectorOfArgothElementalToken(this);
    }
}
