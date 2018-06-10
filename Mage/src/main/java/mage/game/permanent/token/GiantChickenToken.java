
package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author ciaccona007
 */

public final class GiantChickenToken extends TokenImpl {

    public GiantChickenToken() {
        super("Giant Chicken", "4/4 red Giant Chicken creature token");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.GIANT);
        subtype.add(SubType.CHICKEN);
        power = new MageInt(4);
        toughness = new MageInt(4);
    }

    public GiantChickenToken(final GiantChickenToken token) {
        super(token);
    }

    public GiantChickenToken copy() {
        return new GiantChickenToken(this);
    }
}
