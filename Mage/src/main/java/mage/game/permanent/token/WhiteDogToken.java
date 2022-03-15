package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author htrajan
 */
public final class WhiteDogToken extends TokenImpl {

    public WhiteDogToken() {
        super("Dog Token", "1/1 white Dog creature token");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.DOG);

        color.setWhite(true);
        power = new MageInt(1);
        toughness = new MageInt(1);

        availableImageSetCodes.addAll(Arrays.asList("M21"));
    }

    private WhiteDogToken(final WhiteDogToken token) {
        super(token);
    }

    public WhiteDogToken copy() {
        return new WhiteDogToken(this);
    }

}
