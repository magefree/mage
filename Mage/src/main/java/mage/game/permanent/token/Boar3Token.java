package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author TheElk801
 */
public final class Boar3Token extends TokenImpl {

    public Boar3Token() {
        super("Boar Token", "3/1 green Boar creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.BOAR);
        power = new MageInt(3);
        toughness = new MageInt(1);

        availableImageSetCodes = Arrays.asList("VOW");
    }

    public Boar3Token(final Boar3Token token) {
        super(token);
    }

    public Boar3Token copy() {
        return new Boar3Token(this);
    }
}
