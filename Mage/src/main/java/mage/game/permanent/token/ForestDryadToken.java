package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author TheElk801
 */
public final class ForestDryadToken extends TokenImpl {

    public ForestDryadToken() {
        super("Forest Dryad Token", "1/1 green Forest Dryad land creature token");
        cardType.add(CardType.LAND);
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.FOREST, SubType.DRYAD);
        power = new MageInt(1);
        toughness = new MageInt(1);

        availableImageSetCodes = Arrays.asList("BRO");
    }

    public ForestDryadToken(final ForestDryadToken token) {
        super(token);
    }

    public ForestDryadToken copy() {
        return new ForestDryadToken(this);
    }
}
