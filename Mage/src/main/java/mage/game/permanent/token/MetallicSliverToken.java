

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

import java.util.Arrays;

/**
 *
 * @author spjspj
 */
public final class MetallicSliverToken extends TokenImpl {

    public MetallicSliverToken() {
        super("Metallic Sliver", "1/1 colorless Sliver artifact creature token named Metallic Sliver");
        cardType.add(CardType.CREATURE);
        cardType.add(CardType.ARTIFACT);
        subtype.add(SubType.SLIVER);
        power = new MageInt(1);
        toughness = new MageInt(1);

        availableImageSetCodes = Arrays.asList("TSR");
    }

    public MetallicSliverToken(final MetallicSliverToken token) {
        super(token);
    }

    public MetallicSliverToken copy() {
        return new MetallicSliverToken(this);
    }
}
