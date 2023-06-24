package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class EyesOfTheWisentElementalToken extends TokenImpl {

    public EyesOfTheWisentElementalToken() {
        super("Elemental Token", "4/4 green Elemental creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.ELEMENTAL);
        power = new MageInt(4);
        toughness = new MageInt(4);
    }

    public EyesOfTheWisentElementalToken(final EyesOfTheWisentElementalToken token) {
        super(token);
    }

    public EyesOfTheWisentElementalToken copy() {
        return new EyesOfTheWisentElementalToken(this);
    }
}
