package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;

/**
 *
 * @author Loki
 */
public class KithkinToken extends Token{

    public KithkinToken() {
        this("LRW");
    }

    public KithkinToken(String expansionSetCode) {
        super("Kithkin", "1/1 white Kithkin Soldier creature token");
        setOriginalExpansionSetCode(expansionSetCode);
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add("Kithkin");
        subtype.add("Soldier");
        power = new MageInt(1);
        toughness = new MageInt(1);
    }
}
