

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
public final class CatToken extends TokenImpl {

    final static private List<String> tokenImageSets = new ArrayList<>();

    static {
        tokenImageSets.addAll(Arrays.asList("SOM", "M13", "M14", "C14", "C15", "C17"));
    }

    public CatToken() {
        this(null, 0);
    }

    public CatToken(String setCode) {
        this(setCode, 0);
    }

    public CatToken(String setCode, int tokenType) {
        super("Cat", "2/2 white Cat creature token");
        availableImageSetCodes = tokenImageSets;
        setOriginalExpansionSetCode(setCode);
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.CAT);
        power = new MageInt(2);
        toughness = new MageInt(2);
    }

    public CatToken(final CatToken token) {
        super(token);
    }

    public CatToken copy() {
        return new CatToken(this);
    }
    
}
