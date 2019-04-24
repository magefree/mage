

package mage.game.permanent.token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

/**
 *
 * @author fireshoes
 */
public final class WurmToken3 extends TokenImpl {

    final static private List<String> tokenImageSets = new ArrayList<>();

    static {
        tokenImageSets.addAll(Arrays.asList("AKH"));
    }

    public WurmToken3() {
        super("Wurm", "5/5 green Wurm creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.WURM);
        power = new MageInt(5);
        toughness = new MageInt(5);
    }

    public WurmToken3(final WurmToken3 token) {
        super(token);
    }

    public WurmToken3 copy() {
        return new WurmToken3(this);
    }
}
