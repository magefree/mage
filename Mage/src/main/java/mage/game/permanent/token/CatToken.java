package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author LoneFox
 */
public final class CatToken extends TokenImpl {

    public CatToken() {
        super("Cat Token", "2/2 white Cat creature token");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.CAT);
        power = new MageInt(2);
        toughness = new MageInt(2);

        availableImageSetCodes = Arrays.asList("PMEI", "C14", "C15", "C17", "C18", "M13", "M14", "SOM", "CMR", "2XM", "ONE");
    }

    public CatToken(final CatToken token) {
        super(token);
    }

    public CatToken copy() {
        return new CatToken(this);
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);
    }
}
