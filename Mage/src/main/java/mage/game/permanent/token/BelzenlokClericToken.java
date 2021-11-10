
package mage.game.permanent.token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author spjspj
 */
public final class BelzenlokClericToken extends TokenImpl {

    static final private List<String> tokenImageSets = new ArrayList<>();
    static {
        tokenImageSets.addAll(Arrays.asList("DOM"));
    }

    public BelzenlokClericToken() {
        super("Cleric Token", "0/1 black Cleric creature token");
        availableImageSetCodes = tokenImageSets;
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.CLERIC);
        color.setBlack(true);
        power = new MageInt(0);
        toughness = new MageInt(1);
    }

    public BelzenlokClericToken(final BelzenlokClericToken token) {
        super(token);
    }

    public BelzenlokClericToken copy() {
        return new BelzenlokClericToken(this);
    }
}
