package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author Quercitron
 */
public final class MinionToken extends TokenImpl {

    public MinionToken() {
        super("Minion Token", "1/1 black Minion creature token");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.MINION);
        color.setBlack(true);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    protected MinionToken(final MinionToken token) {
        super(token);
    }

    public MinionToken copy() {
        return new MinionToken(this);
    }
}
