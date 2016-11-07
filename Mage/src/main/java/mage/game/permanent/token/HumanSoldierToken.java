package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;

public class HumanSoldierToken extends Token {

    public HumanSoldierToken() {
        super("Human Soldier", "1/1 white Human Soldier creature token");
        cardType.add(CardType.CREATURE);
        subtype.add("Human");
        subtype.add("Soldier");
        color.setWhite(true);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }
}
