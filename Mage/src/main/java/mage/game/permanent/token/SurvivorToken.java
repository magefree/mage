
package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class SurvivorToken extends TokenImpl {

    public SurvivorToken() {
        super("Survivor Token", "1/1 red Survivor creature token");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.SURVIVOR);
        color.setRed(true);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    public SurvivorToken(final SurvivorToken token) {
        super(token);
    }

    public SurvivorToken copy() {
        return new SurvivorToken(this);
    }
}
