
package mage.game.permanent.token;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

/**
 * @author L_J
 */
public final class RabidSheepToken extends TokenImpl {

    public RabidSheepToken() {
        super("Rabid Sheep", "2/2 green Sheep creature token named Rabid Sheep");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.SHEEP);
        color.setGreen(true);
        power = new MageInt(2);
        toughness = new MageInt(2);
    }

    protected RabidSheepToken(final RabidSheepToken token) {
        super(token);
    }

    public RabidSheepToken copy() {
        return new RabidSheepToken(this);
    }
}
