package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author spjspj
 */
public final class PestToken extends TokenImpl {

    public PestToken() {
        super("Pest Token", "0/1 colorless Pest artifact creature token");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.PEST);
        power = new MageInt(0);
        toughness = new MageInt(1);

        availableImageSetCodes = Arrays.asList("HOP", "MRD");
    }

    public PestToken(final PestToken token) {
        super(token);
    }

    public PestToken copy() {
        return new PestToken(this);
    }
}
