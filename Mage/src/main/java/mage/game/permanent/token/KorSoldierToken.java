package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
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
    }

    public KorSoldierToken(final KorSoldierToken token) {
        super(token);
    }

    public KorSoldierToken copy() {
        return new KorSoldierToken(this);
    }
}
