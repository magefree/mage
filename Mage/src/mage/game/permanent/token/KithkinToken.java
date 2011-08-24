package mage.game.permanent.token;

import mage.Constants;
import mage.MageInt;

/**
 *
 * @author Loki
 */
public class KithkinToken extends Token{

    public KithkinToken() {
        super("Kithkin", "1/1 white Kithkin Soldier creature token");
        cardType.add(Constants.CardType.CREATURE);
        color.setWhite(true);
        subtype.add("Kithkin");
        subtype.add("Soldier");
        power = new MageInt(1);
        toughness = new MageInt(1);
    }
}
