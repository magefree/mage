
package mage.game.permanent.token;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

/**
 *
 * @author spjspj
 */
public final class InexorableBlobOozeToken extends TokenImpl {

    public InexorableBlobOozeToken() {
        super("Ooze Token", "3/3 green Ooze creature token");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.OOZE);
        color.setGreen(true);
        power = new MageInt(3);
        toughness = new MageInt(3);
    }

    public InexorableBlobOozeToken(final InexorableBlobOozeToken token) {
        super(token);
    }

    public InexorableBlobOozeToken copy() {
        return new InexorableBlobOozeToken(this);
    }
}
