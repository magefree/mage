package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author FenrisulfrX
 */
public final class MinionToken extends TokenImpl {

    public MinionToken() {
        this("DDE");
    }

    public MinionToken(String setCode) {
        super("Phyrexian Minion Token", "X/X black Phyrexian Minion creature token");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.PHYREXIAN);
        subtype.add(SubType.MINION);
        color.setBlack(true);
        power = new MageInt(0);
        toughness = new MageInt(0);
    }

    public MinionToken(final MinionToken token) {
        super(token);
    }

    public MinionToken copy() {
        return new MinionToken(this);
    }
}
