package mage.game.permanent.token;

import mage.constants.CardType;
import mage.MageInt;
import mage.abilities.keyword.VigilanceAbility;

/**
 *
 * @author LevelX2
 */
public class KnightToken extends Token {

    public KnightToken() {
        super("Knight", "2/2 white Knight creature token with vigilance");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        
        subtype.add("Knight");
        power = new MageInt(2);
        toughness = new MageInt(2);
        this.addAbility(VigilanceAbility.getInstance());
        setOriginalExpansionSetCode("RTR");
    }
}
