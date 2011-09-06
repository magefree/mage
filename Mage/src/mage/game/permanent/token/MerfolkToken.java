package mage.game.permanent.token;

import mage.Constants;
import mage.MageInt;

/**
 *
 * @author Loki
 */
public class MerfolkToken extends Token {

    public MerfolkToken() {
        super("Merfolk", "1/1 blue Merfolk Wizard creature token");
        cardType.add(Constants.CardType.CREATURE);
        color.setBlue(true);
        subtype.add("Merfolk");
        subtype.add("Wizard");
        power = new MageInt(1);
        toughness = new MageInt(1);
    }
}
