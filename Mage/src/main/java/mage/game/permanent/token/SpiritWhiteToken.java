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
        super("Spirit", "1/1 white Spirit creature token with flying");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.SPIRIT);
        color.setWhite(true);
        power = new MageInt(1);
        toughness = new MageInt(1);

        addAbility(FlyingAbility.getInstance());

        availableImageSetCodes = Arrays.asList("AVR", "C14", "CNS", "DDC", "DDK", "FRF", "ISD", "KTK", "M15", "MM2", "SHM",
                "SOI", "EMA", "C16", "MM3", "CMA", "E01", "ANA", "GPT", "RAV", "EMN", "RNA", "M20", "C20", "CMR", "KHM",
                "MID", "VOW");
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);
        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("AVR")) {
            setTokenType(1);
        }
        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("C16")) {
            setTokenType(2);
        }
        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("EMA")) {
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
