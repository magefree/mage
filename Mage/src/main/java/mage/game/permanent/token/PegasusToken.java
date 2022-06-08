package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author LoneFox
 */
public final class PegasusToken extends TokenImpl {

    public PegasusToken() {
        super("Pegasus Token", "1/1 white Pegasus creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.PEGASUS);
        power = new MageInt(1);
        toughness = new MageInt(1);

        addAbility(FlyingAbility.getInstance());

        availableImageSetCodes = Arrays.asList("C14", "C19", "TSP", "THB", "KHC", "CLB");
    }

    public PegasusToken(final PegasusToken token) {
        super(token);
    }

    public PegasusToken copy() {
        return new PegasusToken(this);
    }
}
