

package mage.game.permanent.token;
import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.Arrays;

/**
 *
 * @author spjspj
 */
public final class RagavanToken extends TokenImpl {

    public RagavanToken() {
        super("Ragavan", "Ragavan, a legendary 2/1 red Monkey creature token");
        this.supertype.add(SuperType.LEGENDARY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
        this.color.setRed(true);
        this.subtype.add(SubType.MONKEY);
        this.cardType.add(CardType.CREATURE);

        availableImageSetCodes = Arrays.asList("AER", "DMC");
    }

    public RagavanToken(final RagavanToken token) {
        super(token);
    }

    public RagavanToken copy() {
        return new RagavanToken(this);
    }
}
