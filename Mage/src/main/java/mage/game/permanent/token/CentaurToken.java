

package mage.game.permanent.token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.util.RandomUtil;

/**
 *
 * @author LevelX2
 */
public final class CentaurToken extends TokenImpl {

    final static private List<String> tokenImageSets = new ArrayList<>();

    static {
        tokenImageSets.addAll(Arrays.asList("RTR", "MM3"));
    }

    public CentaurToken() {
        super("Centaur", "3/3 green Centaur creature token");
        cardType.add(CardType.CREATURE);
        setTokenType(RandomUtil.nextInt(2) +1); // randomly take image 1 or 2
        color.setGreen(true);
        subtype.add(SubType.CENTAUR);
        power = new MageInt(3);
        toughness = new MageInt(3);
    }

    public CentaurToken(final CentaurToken token) {
        super(token);
    }

    public CentaurToken copy() {
        return new CentaurToken(this);
    }
}
