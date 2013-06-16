package mage.game.permanent.token;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;

public class AngelToken extends Token {
    public AngelToken() {
        super("Angel", "4/4 white Angel creature token with flying");
        cardType.add(CardType.CREATURE);
        color = ObjectColor.WHITE;
        subtype.add("Angel");
        power = new MageInt(4);
        toughness = new MageInt(4);
        addAbility(FlyingAbility.getInstance());
    }
}
