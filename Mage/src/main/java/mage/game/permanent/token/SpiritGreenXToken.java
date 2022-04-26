package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author TheElk801
 */
public final class SpiritGreenXToken extends TokenImpl {

    public SpiritGreenXToken(int xValue) {
        super("Spirit Token", "X/X green Spirit creature token");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.SPIRIT);
        color.setGreen(true);
        power = new MageInt(xValue);
        toughness = new MageInt(xValue);

        availableImageSetCodes = Arrays.asList("NEO");
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("NEO")) {
            setTokenType(2);
        }
    }

    public SpiritGreenXToken(final SpiritGreenXToken token) {
        super(token);
    }

    public SpiritGreenXToken copy() {
        return new SpiritGreenXToken(this);
    }
}
