package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author nantuko
 */
public final class SpiritWhiteToken extends TokenImpl {

    public SpiritWhiteToken() {
        super("Spirit Token", "1/1 white Spirit creature token with flying");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.SPIRIT);
        color.setWhite(true);
        power = new MageInt(1);
        toughness = new MageInt(1);

        addAbility(FlyingAbility.getInstance());

        availableImageSetCodes = Arrays.asList("AVR", "C14", "CNS", "DDC", "DDK", "FRF", "ISD", "KTK", "M15", "MM2", "SHM",
                "SOI", "EMA", "C16", "MM3", "CMA", "E01", "RAV", "EMN", "M20", "C19", "C20", "CMR", "KHM",
                "MID", "VOW", "UMA", "BBD", "IMA", "CM2", "MD1", "DVD", "DDQ", "CN2", "A25", "GK2", "2X2", "ONC");
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);
        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("AVR")) {
            setTokenType(2);
        }
        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("C16")) {
            setTokenType(2);
        }
        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("EMA")) {
            setTokenType(2);
        }
        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("UMA")) {
            setTokenType(1);
        }
        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("A25")) {
            setTokenType(2);
        }
        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("2X2")) {
            setTokenType(2);
        }
    }

    public SpiritWhiteToken(final SpiritWhiteToken token) {
        super(token);
    }

    @Override
    public SpiritWhiteToken copy() {
        return new SpiritWhiteToken(this);
    }
}
