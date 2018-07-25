

package mage.game.permanent.token;

import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author NinthWorld
 */
public final class ZergToken extends TokenImpl {

    public ZergToken() {
        super("Zerg", "1/1 green Zerg creature token", 1, 1);
        this.setOriginalExpansionSetCode("DDSC");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.ZERG);
    }

    public ZergToken(final ZergToken token) {
        super(token);
    }

    @Override
    public ZergToken copy() {
        return new ZergToken(this);
    }
}
