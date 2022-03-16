

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

/**
 *
 * @author spjspj
 */
public final class CaribouToken extends TokenImpl {

    public CaribouToken() {
        super("Caribou Token", "0/1 white Caribou creature token");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.CARIBOU);
        power = new MageInt(0);
        toughness = new MageInt(1);
    }

    public CaribouToken(final CaribouToken token) {
        super(token);
    }

    public CaribouToken copy() {
        return new CaribouToken(this);
    }
}
