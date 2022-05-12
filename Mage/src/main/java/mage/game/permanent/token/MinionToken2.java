

package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Quercitron
 */
public final class MinionToken2 extends TokenImpl {

    public MinionToken2() {
        this("PCY");
    }

    public MinionToken2(String setCode) {
        super("Minion Token", "1/1 black Minion creature token");
        this.setOriginalExpansionSetCode(setCode);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.MINION);
        color.setBlack(true);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    public MinionToken2(final MinionToken2 token) {
        super(token);
    }

    public MinionToken2 copy() {
        return new MinionToken2(this);
    }
}
