package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;
import java.util.Arrays;

/**
 * @author TheElk801
 */
public final class RogueToken extends TokenImpl {

    public RogueToken() {
        super("Rogue Token", "2/2 black Rogue creature token");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.ROGUE);
        color.setBlack(true);
        power = new MageInt(2);
        toughness = new MageInt(2);

        availableImageSetCodes = Arrays.asList("SNC");
    }

    public RogueToken(final RogueToken token) {
        super(token);
    }

    public RogueToken copy() {
        return new RogueToken(this);
    }
}
