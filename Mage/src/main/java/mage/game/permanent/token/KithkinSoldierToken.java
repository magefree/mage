package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author Loki
 */
public final class KithkinSoldierToken extends TokenImpl {

    public KithkinSoldierToken() {
        super("Kithkin Soldier Token", "1/1 white Kithkin Soldier creature token");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.KITHKIN);
        subtype.add(SubType.SOLDIER);
        power = new MageInt(1);
        toughness = new MageInt(1);

        availableImageSetCodes = Arrays.asList("CMD", "EVE", "LRW", "MMA", "MOR", "SHM", "MMA", "KHC");
    }

    public KithkinSoldierToken(final KithkinSoldierToken token) {
        super(token);
    }

    public KithkinSoldierToken copy() {
        return new KithkinSoldierToken(this);
    }
}
