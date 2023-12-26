
package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author notgreat
 */
public final class AlienToken extends TokenImpl {

    public AlienToken() {
        super("Alien Token", "2/2 white Alien creature token");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.ALIEN);
        power = new MageInt(2);
        toughness = new MageInt(2);
    }

    protected AlienToken(final AlienToken token) {
        super(token);
    }

    public AlienToken copy() {
        return new AlienToken(this);
    }
}
