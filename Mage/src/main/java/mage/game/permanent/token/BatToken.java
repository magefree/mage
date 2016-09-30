package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;

public class BatToken extends Token {

    public BatToken() {
        super("Bat", "1/1 black Bat creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add("Bat");
        power = new MageInt(1);
        toughness = new MageInt(1);
        this.addAbility(FlyingAbility.getInstance());

        this.setOriginalExpansionSetCode("MMA");
    }
}
