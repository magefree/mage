package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author spjspj
 */
public final class FrogLizardToken extends TokenImpl {

    public FrogLizardToken() {
        super("Frog Lizard Token", "3/3 green Frog Lizard creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.FROG);
        subtype.add(SubType.LIZARD);
        power = new MageInt(3);
        toughness = new MageInt(3);

        availableImageSetCodes = Arrays.asList("RNA", "C15", "C21");
    }

    public FrogLizardToken(final FrogLizardToken token) {
        super(token);
    }

    public FrogLizardToken copy() {
        return new FrogLizardToken(this);
    }
}
