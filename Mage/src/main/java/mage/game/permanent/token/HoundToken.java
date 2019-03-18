

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

/**
 *
 * @author spjspj
 */
public final class HoundToken extends TokenImpl {

    public HoundToken() {
        super("Hound", "1/1 green Hound creature token");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.HOUND);

        color.setGreen(true);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    public HoundToken(final HoundToken token) {
        super(token);
    }

    public HoundToken copy() {
        return new HoundToken(this);
    }
}
