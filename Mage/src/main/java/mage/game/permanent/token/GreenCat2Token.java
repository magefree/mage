package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author TheElk801
 */
public final class GreenCat2Token extends TokenImpl {

    public GreenCat2Token() {
        super("Cat Token", "2/2 green Cat creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.CAT);
        power = new MageInt(2);
        toughness = new MageInt(2);

        availableImageSetCodes = Arrays.asList("M21", "KHM", "DMR");
    }

    private GreenCat2Token(final GreenCat2Token token) {
        super(token);
    }

    public GreenCat2Token copy() {
        return new GreenCat2Token(this);
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("M21")) {
            this.setTokenType(2);
        }
        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("DMR")) {
            this.setTokenType(2);
        }
    }
}
