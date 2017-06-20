package mage.game.permanent.token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import mage.MageInt;
import mage.abilities.keyword.VigilanceAbility;
import mage.constants.CardType;

/**
 *
 * @author LevelX2
 */
public class KnightToken extends Token {

    final static private List<String> tokenImageSets = new ArrayList<>();

    static {
        tokenImageSets.addAll(Arrays.asList("ORI", "RTR", "C15", "CMA"));
    }

    public KnightToken() {
        super("Knight", "2/2 white Knight creature token with vigilance");
        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("C15")) {
            setTokenType(2);
        }
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add("Knight");
        power = new MageInt(2);
        toughness = new MageInt(2);
        this.addAbility(VigilanceAbility.getInstance());

        availableImageSetCodes = tokenImageSets;
    }
}
