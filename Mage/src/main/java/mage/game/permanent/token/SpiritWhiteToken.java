package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author nantuko
 */
public final class SpiritWhiteToken extends TokenImpl {

    static final private List<String> tokenImageSets = new ArrayList<>();

    static {
        tokenImageSets.addAll(Arrays.asList("AVR", "C14", "CNS", "DDC", "DDK", "FRF", "ISD", "KTK", "M15", "MM2", "SHM",
                "SOI", "EMA", "C16", "MM3", "CMA", "E01", "ANA", "RNA", "M20"));
    }

    public SpiritWhiteToken() {
        this(null, 0);
    }

    public SpiritWhiteToken(String setCode) {
        this(setCode, 0);
    }

    public SpiritWhiteToken(String setCode, int tokenType) {
        super("Spirit", "1/1 white Spirit creature token with flying");
        availableImageSetCodes = tokenImageSets;
        setOriginalExpansionSetCode(setCode);
        if (tokenType > 0) {
            setTokenType(tokenType);
        }
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.SPIRIT);
        color.setWhite(true);
        power = new MageInt(1);
        toughness = new MageInt(1);

        addAbility(FlyingAbility.getInstance());
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
