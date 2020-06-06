

package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author htrajan
 */
public final class CatToken3 extends TokenImpl {

     public CatToken3() {
        super("Cat", "1/1 green Cat creature token");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.CAT);

        color.setGreen(true);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    public CatToken3(final CatToken3 token) {
        super(token);
    }

    public CatToken3 copy() {
        return new CatToken3(this);
    }
    
}
