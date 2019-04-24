

package mage.game.permanent.token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.keyword.TrampleAbility;

/**
 *
 * @author LoneFox
 */
public final class WurmToken2 extends TokenImpl {

    final static private List<String> tokenImageSets = new ArrayList<>();

    static {
        tokenImageSets.addAll(Arrays.asList("RTR", "MM3"));
    }

    public WurmToken2() {
        super("Wurm", "5/5 green Wurm creature token with trample");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.WURM);
        power = new MageInt(5);
        toughness = new MageInt(5);
        addAbility(TrampleAbility.getInstance());
    }

    public WurmToken2(final WurmToken2 token) {
        super(token);
    }

    public WurmToken2 copy() {
        return new WurmToken2(this);
    }
}
