package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

/**
 * @author TheElk801
 */
public final class AngeloToken extends TokenImpl {

    public AngeloToken() {
        super("Angelo", "Angelo, a legendary 1/1 green and white Dog creature token");
        supertype.add(SuperType.LEGENDARY);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.DOG);

        color.setGreen(true);
        color.setWhite(true);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    private AngeloToken(final AngeloToken token) {
        super(token);
    }

    public AngeloToken copy() {
        return new AngeloToken(this);
    }
}
