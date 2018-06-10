

package mage.game.permanent.token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class GoatToken extends TokenImpl {

    final static private List<String> tokenImageSets = new ArrayList<>();

    static {
        tokenImageSets.addAll(Arrays.asList("EVE", "M13", "M14", "C14"));
    }

    public GoatToken() {
        this(null, 0);
    }

    public GoatToken(String setCode) {
        this(setCode, 0);
    }

    public GoatToken(String setCode, int tokenType) {
        super("Goat", "0/1 white Goat creature token");
        availableImageSetCodes = tokenImageSets;
        setOriginalExpansionSetCode(setCode);
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.GOAT);
        power = new MageInt(0);
        toughness = new MageInt(1);
    }

    public GoatToken(final GoatToken token) {
        super(token);
    }

    public GoatToken copy() {
        return new GoatToken(this);
    }   
}
