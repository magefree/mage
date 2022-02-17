package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.LifelinkAbility;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author TheElk801
 */
public final class EdgarMarkovsCoffinVampireToken extends TokenImpl {

    public EdgarMarkovsCoffinVampireToken() {
        super("Vampire", "1/1 white and black Vampire creature token with lifelink");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        color.setBlack(true);
        subtype.add(SubType.VAMPIRE);
        power = new MageInt(1);
        toughness = new MageInt(1);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        availableImageSetCodes = Arrays.asList("VOW");
    }

    public EdgarMarkovsCoffinVampireToken(final EdgarMarkovsCoffinVampireToken token) {
        super(token);
    }

    public EdgarMarkovsCoffinVampireToken copy() {
        return new EdgarMarkovsCoffinVampireToken(this);
    }
}
