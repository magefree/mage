

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

/**
 *
 * @author spjspj
 */
public final class PincherToken extends TokenImpl {

    public PincherToken() {
        super("Pincher Token", "2/2 colorless Pincher creature token");
        setOriginalExpansionSetCode("5ND");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.PINCHER);
        power = new MageInt(2);
        toughness = new MageInt(2);

    }
    public PincherToken(final PincherToken token) {
        super(token);
    }

    public PincherToken copy() {
        return new PincherToken(this);
    }
}
