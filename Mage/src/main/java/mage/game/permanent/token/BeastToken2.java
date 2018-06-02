
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
public final class BeastToken2 extends TokenImpl {

    final static private List<String> tokenImageSets = new ArrayList<>();

    static {
        tokenImageSets.addAll(Arrays.asList("ZEN", "C14", "DDD", "C15", "DD3GVL", "MM3", "CMA", "E01"));
    }

    public BeastToken2() {
        this(null, 0);
    }

    public BeastToken2(String setCode) {
        this(setCode, 0);
    }

    public BeastToken2(String setCode, int tokenType) {
        super("Beast", "4/4 green Beast creature token");
        availableImageSetCodes = tokenImageSets;
        setOriginalExpansionSetCode(setCode);
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.BEAST);
        power = new MageInt(4);
        toughness = new MageInt(4);
    }

    public BeastToken2(final BeastToken2 token) {
        super(token);
    }

    @Override
    public BeastToken2 copy() {
        return new BeastToken2(this);
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);
        if (getOriginalExpansionSetCode().equals("C14") || getOriginalExpansionSetCode().equals("DDD") || getOriginalExpansionSetCode().equals("DD3GVL") || getOriginalExpansionSetCode().equals("MM3")) {
            this.setTokenType(2);
        }
    }
}
