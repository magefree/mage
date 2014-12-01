package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;

public class AngelToken extends Token {
    
    public AngelToken() {
        this("M14");
    }

    public AngelToken(String setCode) {
        super("Angel", "4/4 white Angel creature token with flying");
        this.setOriginalExpansionSetCode(setCode);
        cardType.add(CardType.CREATURE);
        color.setWhite(true);

        subtype.add("Angel");
        power = new MageInt(4);
        toughness = new MageInt(4);
        addAbility(FlyingAbility.getInstance());

    }
}
