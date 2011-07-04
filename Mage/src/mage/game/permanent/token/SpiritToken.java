package mage.game.permanent.token;

import mage.Constants;
import mage.MageInt;

/**
 * @author Loki
 */
public class SpiritToken extends Token {
    public SpiritToken() {
        super("Spirit", "a 1/1 colorless Spirit creature token");
        cardType.add(Constants.CardType.CREATURE);
        subtype.add("Spirit");
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

}
