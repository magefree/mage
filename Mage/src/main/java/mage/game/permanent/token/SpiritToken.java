package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author Loki
 */
public final class SpiritToken extends TokenImpl {

    public SpiritToken() {
        super("Spirit Token", "1/1 colorless Spirit creature token");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.SPIRIT);
        power = new MageInt(1);
        toughness = new MageInt(1);

        availableImageSetCodes = Arrays.asList("C16", "EMA", "NEO", "VOC", "A25");
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("EMA")) {
            setTokenType(1);
        }

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("NEO")) {
            setTokenType(1);
        }

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("C16")) {
            setTokenType(1);
        }

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("A25")) {
            setTokenType(1);
        }

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("VOC")) {
            setTokenType(1);
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