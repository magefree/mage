

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

/**
 *
 * @author spjspj
 */
public final class ZendikarsRoilElementalToken extends TokenImpl {

    public ZendikarsRoilElementalToken() {
        super("Elemental", "2/2 green Elemental creature token");
        cardType.add(CardType.CREATURE);
        setOriginalExpansionSetCode("ORI");
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
