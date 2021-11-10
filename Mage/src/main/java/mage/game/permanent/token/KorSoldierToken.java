

package mage.game.permanent.token;

import java.util.Arrays;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

/**
 *
 * @author LoneFox
 */
public final class KorSoldierToken extends TokenImpl {

    public KorSoldierToken() {
        super("Kor Soldier Token", "1/1 white Kor Soldier creature token");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.KOR);
        subtype.add(SubType.SOLDIER);
        power = new MageInt(1);
        toughness = new MageInt(1);
        availableImageSetCodes.addAll(Arrays.asList("C14", "ZEN"));                                                                                               
    }
    public KorSoldierToken(final KorSoldierToken token) {
        super(token);
    }

    public KorSoldierToken copy() {
        return new KorSoldierToken(this);
    }
}
