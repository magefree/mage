
package mage.game.permanent.token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class GermToken extends TokenImpl {

    final static private List<String> tokenImageSets = new ArrayList<>();

    static {
        tokenImageSets.addAll(Arrays.asList("NPH", "MBS", "SOM", "EMA", "C16"));
    }

    public GermToken() {
        this(null, 0);
    }

    public GermToken(String setCode) {
        this(setCode, 0);
    }

    public GermToken(String setCode, int tokenType) {
        super("Germ", "0/0 black Germ creature token");
        availableImageSetCodes = tokenImageSets;
        setOriginalExpansionSetCode(setCode);
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.GERM);
        power = new MageInt(0);
        toughness = new MageInt(0);
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);
    }

    public GermToken(final GermToken token) {
        super(token);
    }

    @Override
    public GermToken copy() {
        return new GermToken(this);
    }
}
