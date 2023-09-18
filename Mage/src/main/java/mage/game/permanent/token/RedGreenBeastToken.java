
package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class RedGreenBeastToken extends TokenImpl {

    public RedGreenBeastToken() {
        super("Beast Token", "4/4 red and green Beast creature token with trample");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        color.setGreen(true);
        subtype.add(SubType.BEAST);
        power = new MageInt(4);
        toughness = new MageInt(4);

        this.addAbility(TrampleAbility.getInstance());
    }

    private RedGreenBeastToken(final RedGreenBeastToken token) {
        super(token);
    }

    public RedGreenBeastToken copy() {
        return new RedGreenBeastToken(this);
    }
}
