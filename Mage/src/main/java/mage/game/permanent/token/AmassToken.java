
package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class AmassToken extends TokenImpl {

    public AmassToken() {
        super("Zombie Army", "0/0 black Zombie Army creature token");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.ZOMBIE);
        subtype.add(SubType.ARMY);
        power = new MageInt(0);
        toughness = new MageInt(0);
    }

    private AmassToken(final AmassToken token) {
        super(token);
    }

    @Override
    public AmassToken copy() {
        return new AmassToken(this);
    }
}
