package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

public class HumanSoldierToken extends Token {

    public HumanSoldierToken() {
        super("Human Soldier", "1/1 white Human Soldier creature token");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.HUMAN);
        subtype.add(SubType.SOLDIER);
        color.setWhite(true);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }
}
