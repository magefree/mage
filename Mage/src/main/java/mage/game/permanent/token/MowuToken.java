package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.Arrays;

/**
 *
 * @author TheElk801
 */
public final class MowuToken extends TokenImpl {

    public MowuToken() {
        super("Mowu", "Mowu, a legendary 3/3 green Dog creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        this.addSuperType(SuperType.LEGENDARY);
        subtype.add(SubType.DOG);
        power = new MageInt(3);
        toughness = new MageInt(3);

        availableImageSetCodes = Arrays.asList("GS1");
    }

    public MowuToken(final MowuToken token) {
        super(token);
    }

    public MowuToken copy() {
        return new MowuToken(this);
    }
}
