package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

public final class PhyrexianMyrToken extends TokenImpl {

    public PhyrexianMyrToken() {
        super("Phyrexian Myr Token", "1/1 colorless Phyrexian Myr artifact creature token");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.PHYREXIAN);
        subtype.add(SubType.MYR);
        power = new MageInt(1);
        toughness = new MageInt(1);

        availableImageSetCodes = Arrays.asList("NPH", "MD1");
    }

    public PhyrexianMyrToken(final PhyrexianMyrToken token) {
        super(token);
    }

    public PhyrexianMyrToken copy() {
        return new PhyrexianMyrToken(this);
    }
}