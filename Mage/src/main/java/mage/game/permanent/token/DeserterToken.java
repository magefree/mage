
package mage.game.permanent.token;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

/**
 *
 * @author spjspj
 */
public final class DeserterToken extends TokenImpl {

    public DeserterToken() {
        super("Deserter Token", "0/1 white Deserter creature token");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.DESERTER);
        power = new MageInt(0);
        toughness = new MageInt(1);
    }

    public DeserterToken(final DeserterToken token) {
        super(token);
    }

    public DeserterToken copy() {
        return new DeserterToken(this);
    }
}
