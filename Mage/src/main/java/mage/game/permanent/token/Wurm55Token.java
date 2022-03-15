package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author fireshoes
 */
public final class Wurm55Token extends TokenImpl {

    public Wurm55Token() {
        super("Wurm Token", "5/5 green Wurm creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.WURM);
        power = new MageInt(5);
        toughness = new MageInt(5);

        availableImageSetCodes = Arrays.asList("AKH", "DGM");
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);

        if (getOriginalExpansionSetCode().equals("DGM")) {
            this.setTokenType(2);
        }
    }

    public Wurm55Token(final Wurm55Token token) {
        super(token);
    }

    public Wurm55Token copy() {
        return new Wurm55Token(this);
    }
}
