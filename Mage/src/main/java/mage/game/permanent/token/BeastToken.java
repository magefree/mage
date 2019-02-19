
package mage.game.permanent.token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class BeastToken extends TokenImpl {

    static final private List<String> tokenImageSets = new ArrayList<>();

    static {
        tokenImageSets.addAll(Arrays.asList("C14", "LRW", "M15", "M14", "DDL", "M13", "M12", "DD3GVL", "NPH", "M11", "M10", "EVE", "MM3", "CMA", "E01"));
    }

    public BeastToken() {
        this(null, 0);
    }

    public BeastToken(String setCode) {
        this(setCode, 0);
    }

    public BeastToken(String setCode, int tokenType) {
        super("Beast", "3/3 green Beast creature token");
        availableImageSetCodes = tokenImageSets;
        setOriginalExpansionSetCode(setCode);
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.BEAST);
        power = new MageInt(3);
        toughness = new MageInt(3);

    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);
        if (getOriginalExpansionSetCode().equals("M15")) {
            this.setTokenType(2);
        }
        if (getOriginalExpansionSetCode().equals("DD3GVL") || getOriginalExpansionSetCode().equals("C14") || getOriginalExpansionSetCode().equals("DDD") || getOriginalExpansionSetCode().equals("MM3")) {
            this.setTokenType(1);
        }
    }

    public BeastToken(final BeastToken token) {
        super(token);
    }

    @Override
    public BeastToken copy() {
        return new BeastToken(this); //To change body of generated methods, choose Tools | Templates.
    }
}
