package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author TheElk801
 */
public final class Construct4Token extends TokenImpl {

    public Construct4Token() {
        super("Construct Token", "4/4 colorless Construct artifact creature token");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.CONSTRUCT);
        power = new MageInt(4);
        toughness = new MageInt(4);

        availableImageSetCodes.addAll(Arrays.asList("C18", "M21"));
    }

    public Construct4Token(final Construct4Token token) {
        super(token);
    }

    public Construct4Token copy() {
        return new Construct4Token(this);
    }
}
