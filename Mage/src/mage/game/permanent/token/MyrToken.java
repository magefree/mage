package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;

public class MyrToken extends Token {
    public MyrToken() {
        super("Myr", "1/1 colorless Myr artifact creature token");
        cardType.add(CardType.CREATURE);
        cardType.add(CardType.ARTIFACT);
        subtype.add("Myr");
        power = new MageInt(1);
        toughness = new MageInt(1);
    }
}