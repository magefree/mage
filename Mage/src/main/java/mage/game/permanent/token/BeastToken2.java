package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author LoneFox
 */
public final class BeastToken2 extends TokenImpl {

    public BeastToken2() {
        super("Beast Token", "4/4 green Beast creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.BEAST);
        power = new MageInt(4);
        toughness = new MageInt(4);

        availableImageSetCodes = Arrays.asList("C14", "C15", "C18", "C19", "CMA", "CMD", "GVL", "DDD",
                "E01", "ODY", "SCG", "ZEN", "C20", "ZNC", "CMR", "C21", "MH2", "MID", "IMA", "MM3");
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

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("C14")) {
            this.setTokenType(2);
        }

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("C18")) {
            this.setTokenType(1);
        }

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("C19")) {
            this.setTokenType(2);
        }

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("CMA")) {
            this.setTokenType(2);
        }

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("DDD")) {
            this.setTokenType(2);
        }

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("E01")) {
            this.setTokenType(2);
        }

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("CMR")) {
            this.setTokenType(2);
        }

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("C21")) {
            this.setTokenType(2);
        }

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("GVL")) {
            this.setTokenType(2);
        }

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("MM3")) {
            this.setTokenType(2);
        }
    }
}
