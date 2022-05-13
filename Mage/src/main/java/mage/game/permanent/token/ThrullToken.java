

package mage.game.permanent.token;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Quercitron
 */
public final class ThrullToken extends TokenImpl {

    static final private List<String> tokenImageSets = new ArrayList<>();

    static {
        tokenImageSets.addAll(Collections.singletonList("MM2"));
    }

    public ThrullToken() {
        super("Thrull Token", "1/1 black Thrull creature token");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.THRULL);
        color.setBlack(true);
        power = new MageInt(1);
        toughness = new MageInt(1);

        availableImageSetCodes = tokenImageSets;
    }

    public ThrullToken(final ThrullToken token) {
        super(token);
    }

    public ThrullToken copy() {
        return new ThrullToken(this);
    }
}
