
package mage.game.permanent.token;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

/**
 *
 * @author spjspj
 */
public final class WormHarvestToken extends TokenImpl {

    public WormHarvestToken() {
        super("Worm Token", "1/1 black and green Worm creature token");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        color.setGreen(true);
        subtype.add(SubType.WORM);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    public WormHarvestToken(final WormHarvestToken token) {
        super(token);
    }

    public WormHarvestToken copy() {
        return new WormHarvestToken(this);
    }
}
