package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author TheElk801
 */
public final class Elemental11BlueRedToken extends TokenImpl {

    public Elemental11BlueRedToken() {
        super("Elemental Token", "1/1 blue and red Elemental creature token");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.ELEMENTAL);
        color.setBlue(true);
        color.setRed(true);
        power = new MageInt(1);
        toughness = new MageInt(1);

        availableImageSetCodes = Arrays.asList("MOM");
    }

    public Elemental11BlueRedToken(final Elemental11BlueRedToken token) {
        super(token);
    }

    public Elemental11BlueRedToken copy() {
        return new Elemental11BlueRedToken(this);
    }
}
