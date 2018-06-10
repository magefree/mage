
package mage.game.permanent.token;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class SquirrelToken extends TokenImpl {

    final static private List<String> tokenImageSets = new ArrayList<>();

    static {
        tokenImageSets.addAll(Collections.singletonList("CNS"));
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
