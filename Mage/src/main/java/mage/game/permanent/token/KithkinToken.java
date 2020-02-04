package mage.game.permanent.token;

import java.util.Arrays;
import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class KithkinToken extends TokenImpl {

    public KithkinToken() {
        super("Kithkin Soldier", "1/1 white Kithkin Soldier creature token");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.KITHKIN);
        subtype.add(SubType.SOLDIER);
        power = new MageInt(1);
        toughness = new MageInt(1);
        availableImageSetCodes.addAll(Arrays.asList("LRW", "SHM", "MMA"));
    }

    public KithkinToken(final KithkinToken token) {
        super(token);
    }

    public KithkinToken copy() {
        return new KithkinToken(this);
    }
}
