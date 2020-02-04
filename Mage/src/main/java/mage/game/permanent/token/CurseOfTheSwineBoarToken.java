

package mage.game.permanent.token;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

/**
 *
 * @author spjspj
 */
public final class CurseOfTheSwineBoarToken extends TokenImpl {

    public CurseOfTheSwineBoarToken() {
        super("Boar", "2/2 green Boar creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.BOAR);
        power = new MageInt(2);
        toughness = new MageInt(2);
    }

    public CurseOfTheSwineBoarToken(final CurseOfTheSwineBoarToken token) {
        super(token);
    }

    public CurseOfTheSwineBoarToken copy() {
        return new CurseOfTheSwineBoarToken(this);
    }
}

