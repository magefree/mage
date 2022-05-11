package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.util.RandomUtil;

import java.util.Arrays;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class BeastToken extends TokenImpl {

    public BeastToken() {
        super("Beast Token", "3/3 green Beast creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.BEAST);
        power = new MageInt(3);
        toughness = new MageInt(3);

        availableImageSetCodes = Arrays.asList("5DN", "C14", "C16", "C19", "CMA", "CMD", "CN2", "GVL",
                "DD3C", "DD3GVL", "DDD", "DDL", "DST", "E01", "EVE", "LRW", "M10", "M11", "M12",
                "M13", "M14", "M15", "MM3", "NPH", "PC2", "USG", "M19", "IKO", "M21", "CMR", "C21", "AFC", "MIC", "NEC", "2XM");
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("M15")) {
            this.setTokenType(2);
        }
        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("CMD")) {
            this.setTokenType(2);
        }
        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("GVL")) {
            this.setTokenType(2);
        }

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("DD3C")) {
            setTokenType(RandomUtil.nextInt(2) + 1);
        }
        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("MM3")) {
            setTokenType(RandomUtil.nextInt(2) + 1);
        }
    }

    public BeastToken(final BeastToken token) {
        super(token);
    }

    @Override
    public BeastToken copy() {
        return new BeastToken(this);
    }
}
