package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.LifelinkAbility;
import mage.constants.CardType;

public class VampireKnightToken extends Token {

    public VampireKnightToken() {
        super("Vampire Knight", "1/1 black Vampire Knight creature token with lifelink");
        cardType.add(CardType.CREATURE);
        subtype.add("Vampire");
        subtype.add("Knight");
        color.setBlack(true);
        power = new MageInt(1);
        toughness = new MageInt(1);
        addAbility(LifelinkAbility.getInstance());
    }
}
