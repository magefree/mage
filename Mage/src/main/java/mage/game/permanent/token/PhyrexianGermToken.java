package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author spjspj
 */
public final class PhyrexianGermToken extends TokenImpl {

    public PhyrexianGermToken() {
        super("Phyrexian Germ", "0/0 black Phyrexian Germ creature token");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.PHYREXIAN);
        subtype.add(SubType.GERM);
        power = new MageInt(0);
        toughness = new MageInt(0);

        availableImageSetCodes = Arrays.asList("C14", "C15", "MBS", "MM2", "NPH", "PC2", "MH2");
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);
    }

    public PhyrexianGermToken(final PhyrexianGermToken token) {
        super(token);
    }

    @Override
    public PhyrexianGermToken copy() {
        return new PhyrexianGermToken(this);
    }
}
