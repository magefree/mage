package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author spjspj
 */
public final class Boar2Token extends TokenImpl {

    public Boar2Token() {
        super("Boar Token", "2/2 green Boar creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.BOAR);
        power = new MageInt(2);
        toughness = new MageInt(2);

        availableImageSetCodes = Arrays.asList("THS", "KHC", "C21");
    }

    public Boar2Token(final Boar2Token token) {
        super(token);
    }

    public Boar2Token copy() {
        return new Boar2Token(this);
    }
}

