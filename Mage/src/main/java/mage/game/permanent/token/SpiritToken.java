package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Loki
 */
public final class SpiritToken extends TokenImpl {

    static final private List<String> tokenImageSets = new ArrayList<>();

    static {
        tokenImageSets.addAll(Arrays.asList("CHK", "EMA", "C16", "C19"));
    }

    public SpiritToken() {
        this(null, 0);
    }

    public SpiritToken(String setCode) {
        this(setCode, 0);
    }

    public SpiritToken(String setCode, int tokenType) {
        super("Spirit", "1/1 colorless Spirit creature token");
        availableImageSetCodes = tokenImageSets;
        setOriginalExpansionSetCode(setCode);
        if (tokenType > 0) {
            setTokenType(tokenType);
        }
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.SPIRIT);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("BOK")) {
            setTokenType(2);
        }
    }

    public SpiritToken(final SpiritToken token) {
        super(token);
    }

    @Override
    public SpiritToken copy() {
        return new SpiritToken(this);
    }
}