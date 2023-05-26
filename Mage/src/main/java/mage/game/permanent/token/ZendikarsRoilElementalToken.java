package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class ZendikarsRoilElementalToken extends TokenImpl {

    public ZendikarsRoilElementalToken() {
        super("Elemental Token", "2/2 green Elemental creature token");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.ELEMENTAL);
        color.setGreen(true);
        power = new MageInt(2);
        toughness = new MageInt(2);
    }

    public ZendikarsRoilElementalToken(final ZendikarsRoilElementalToken token) {
        super(token);
    }

    public ZendikarsRoilElementalToken copy() {
        return new ZendikarsRoilElementalToken(this);
    }
}
