
package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author spjspj
 */
public final class WaitingInTheWeedsCatToken extends TokenImpl {

    public WaitingInTheWeedsCatToken() {
        super("Cat", "1/1 green Cat creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.CAT);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }
    
    public WaitingInTheWeedsCatToken(final WaitingInTheWeedsCatToken token) {
        super(token);
    }

    public WaitingInTheWeedsCatToken copy() {
        return new WaitingInTheWeedsCatToken(this);
    }
}
