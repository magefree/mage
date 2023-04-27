package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author TheElk801
 */
public final class PhyrexianHorrorGreenToken extends TokenImpl {

    public PhyrexianHorrorGreenToken() {
        this(0);
    }

    public PhyrexianHorrorGreenToken(int xValue) {
        super("Phyrexian Horror Token", "X/X green Phyrexian Horror creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.PHYREXIAN);
        subtype.add(SubType.HORROR);
        power = new MageInt(xValue);
        toughness = new MageInt(xValue);

        availableImageSetCodes = Arrays.asList("ONE");
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("ONE")) {
            setTokenType(1);
        }
    }

    public PhyrexianHorrorGreenToken(final PhyrexianHorrorGreenToken token) {
        super(token);
    }

    @Override
    public PhyrexianHorrorGreenToken copy() {
        return new PhyrexianHorrorGreenToken(this);
    }
}
