package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.LifelinkAbility;
import mage.constants.CardType;
import mage.constants.SubType;

public class VampireKnightToken extends Token {

    public VampireKnightToken() {
        super("Vampire Knight", "1/1 black Vampire Knight creature token with lifelink");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.VAMPIRE);
        subtype.add(SubType.KNIGHT);
        color.setBlack(true);
        power = new MageInt(1);
        toughness = new MageInt(1);
        addAbility(LifelinkAbility.getInstance());
    }
}
