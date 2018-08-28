
package mage.game.permanent.token;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

/**
 *
 * @author L_J
 */
public class RabidSheepToken extends Token {

    public RabidSheepToken() {
        super("Rabid Sheep", "2/2 green Sheep creature token named Rabid Sheep");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.SHEEP);
        color.setGreen(true);
        power = new MageInt(2);
        toughness = new MageInt(2);
    }
}
