

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

/**
 *
 * @author spjspj
 */
public final class MarathWillOfTheWildElementalToken extends TokenImpl {

    public MarathWillOfTheWildElementalToken() {
        super("Elemental Token", "X/X green Elemental creature token");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.ELEMENTAL);
        color.setGreen(true);
        power = new MageInt(0);
        toughness = new MageInt(0);
    }

    public MarathWillOfTheWildElementalToken(final MarathWillOfTheWildElementalToken token) {
        super(token);
    }

    public MarathWillOfTheWildElementalToken copy() {
        return new MarathWillOfTheWildElementalToken(this);
    }
}
