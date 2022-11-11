package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author North
 */
public final class SquirrelToken extends TokenImpl {

    public SquirrelToken() {
        super("Squirrel Token", "1/1 green Squirrel creature token");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.SQUIRREL);
        color.setGreen(true);
        power = new MageInt(1);
        toughness = new MageInt(1);

        availableImageSetCodes = Arrays.asList("CNS", "ODY", "PCY", "TOR", "ULG", "UNH", "WMA",
                "WTH", "MH1", "MH2", "2XM", "CLB");
    }

    public SquirrelToken(final SquirrelToken token) {
        super(token);
    }

    public SquirrelToken copy() {
        return new SquirrelToken(this);
    }
}
