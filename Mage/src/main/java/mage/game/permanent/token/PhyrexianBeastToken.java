package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author TheElk801
 */
public final class PhyrexianBeastToken extends TokenImpl {

    public PhyrexianBeastToken() {
        super("Phyrexian Beast Token", "4/4 green Phyrexian Beast creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.PHYREXIAN);
        subtype.add(SubType.BEAST);
        power = new MageInt(4);
        toughness = new MageInt(4);

        availableImageSetCodes = Arrays.asList("C21");
    }

    public PhyrexianBeastToken(final PhyrexianBeastToken token) {
        super(token);
    }

    @Override
    public PhyrexianBeastToken copy() {
        return new PhyrexianBeastToken(this);
    }
}
