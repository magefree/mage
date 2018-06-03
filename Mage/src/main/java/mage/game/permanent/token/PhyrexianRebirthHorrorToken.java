
package mage.game.permanent.token;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

/**
 *
 * @author spjspj
 */
public final class PhyrexianRebirthHorrorToken extends TokenImpl {

    public PhyrexianRebirthHorrorToken() {
        super("Horror", "X/X colorless Horror artifact creature token");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.HORROR);
        power = new MageInt(0);
        toughness = new MageInt(0);
    }

    public PhyrexianRebirthHorrorToken(final PhyrexianRebirthHorrorToken token) {
        super(token);
    }

    public PhyrexianRebirthHorrorToken copy() {
        return new PhyrexianRebirthHorrorToken(this);
    }
}
