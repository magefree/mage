package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author North
 */
public final class SquirrelToken extends TokenImpl {

    static final private List<String> tokenImageSets = new ArrayList<>();

    static {
        tokenImageSets.addAll(Arrays.asList("CNS", "MH1"));
    }

    public SquirrelToken() {
        super("Squirrel", "1/1 green Squirrel creature token");
        availableImageSetCodes = tokenImageSets;
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.SQUIRREL);

        color.setGreen(true);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    public SquirrelToken(final SquirrelToken token) {
        super(token);
    }

    public SquirrelToken copy() {
        return new SquirrelToken(this);
    }
}
