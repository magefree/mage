package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.Arrays;

/**
 * @author spjspj
 */
public final class TuktukTheReturnedToken extends TokenImpl {

    public TuktukTheReturnedToken() {
        super("Tuktuk the Returned", "Tuktuk the Returned, a legendary 5/5 colorless Goblin Golem artifact creature token");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        addSuperType(SuperType.LEGENDARY);
        subtype.add(SubType.GOBLIN);
        subtype.add(SubType.GOLEM);
        power = new MageInt(5);
        toughness = new MageInt(5);

        availableImageSetCodes = Arrays.asList("ROE", "C14", "CM2", "2XM");
    }

    public TuktukTheReturnedToken(final TuktukTheReturnedToken token) {
        super(token);
    }

    public TuktukTheReturnedToken copy() {
        return new TuktukTheReturnedToken(this);
    }
}
