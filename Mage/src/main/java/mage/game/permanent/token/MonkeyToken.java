
package mage.game.permanent.token;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

/**
 *
 * @author spjspj
 */
public final class MonkeyToken extends TokenImpl {

    public MonkeyToken() {
        super("Monkey Token", "2/2 green Monkey creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.MONKEY);
        power = new MageInt(2);
        toughness = new MageInt(2);
    }

    public MonkeyToken(final MonkeyToken token) {
        super(token);
    }

    public MonkeyToken copy() {
        return new MonkeyToken(this);
    }
}
