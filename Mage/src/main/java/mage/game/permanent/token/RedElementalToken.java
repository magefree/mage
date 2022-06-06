package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.util.RandomUtil;

import java.util.Arrays;

/**
 * @author spjspj
 */
public final class RedElementalToken extends TokenImpl {

    public RedElementalToken() {
        super("Elemental Token", "1/1 red Elemental creature token");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.ELEMENTAL);
        power = new MageInt(1);
        toughness = new MageInt(1);

        availableImageSetCodes = Arrays.asList("EMA", "M14", "SHM", "MH1", "M20", "RIX", "UMA", "NEC", "DDS");
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("C13")) {
            setTokenType(2);
        }

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("M14")) {
            setTokenType(RandomUtil.nextInt(2) + 1);
        }

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("RIX")) {
            setTokenType(2);
        }

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("SHM")) {
            setTokenType(2);
        }

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("UMA")) {
            setTokenType(RandomUtil.nextInt(2) + 2); // 2..3
        }

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("EMA")) {
            setTokenType(1);
        }
    }

    public RedElementalToken(final RedElementalToken token) {
        super(token);
    }

    public RedElementalToken copy() {
        return new RedElementalToken(this);
    }
}
