package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;

public class KorAllyToken extends Token {

    public KorAllyToken() {
        super("Kor Ally", "1/1 white Kor Ally creature token");
        this.setExpansionSetCodeForImage("BFZ");
        cardType.add(CardType.CREATURE);
        subtype.add("Kor");
        subtype.add("Ally");
        color.setWhite(true);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }
}
