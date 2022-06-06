package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.util.RandomUtil;

import java.util.Arrays;

/**
 * @author LevelX2
 */
public final class CentaurToken extends TokenImpl {

    public CentaurToken() {
        super("Centaur Token", "3/3 green Centaur creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.CENTAUR);
        power = new MageInt(3);
        toughness = new MageInt(3);

        availableImageSetCodes = Arrays.asList("C19", "RTR", "RNA", "MIC", "MM3");
    }

    public CentaurToken(final CentaurToken token) {
        super(token);
    }

    public CentaurToken copy() {
        return new CentaurToken(this);
    }
}
