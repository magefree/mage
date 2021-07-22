package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

public final class MyrToken extends TokenImpl {

    public MyrToken() {
        super("Myr", "1/1 colorless Myr artifact creature token");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.MYR);
        power = new MageInt(1);
        toughness = new MageInt(1);

        availableImageSetCodes = Arrays.asList("C13", "C14", "DST", "MBS", "MM2", "MRD", "NPH", "SOM", "MH1", "C21");
    }

    public MyrToken(final MyrToken token) {
        super(token);
    }

    public MyrToken copy() {
        return new MyrToken(this);
    }
}