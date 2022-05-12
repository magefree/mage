

package mage.game.permanent.token;

import java.util.Arrays;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

/**
 *
 * @author LoneFox
 */
public final class SliverToken extends TokenImpl {

    public SliverToken() {
        super("Sliver Token", "1/1 colorless Sliver creature token");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.SLIVER);
        power = new MageInt(1);
        toughness = new MageInt(1);
        availableImageSetCodes.addAll(Arrays.asList("M14", "M15"));
    }

    public SliverToken(final SliverToken token) {
        super(token);
    }

    public SliverToken copy() {
        return new SliverToken(this);
    }
}
