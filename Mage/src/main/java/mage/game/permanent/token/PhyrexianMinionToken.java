package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author FenrisulfrX
 */
public final class PhyrexianMinionToken extends TokenImpl {

    public PhyrexianMinionToken() {
        this("DDE");
    }

    public PhyrexianMinionToken(String setCode) {
        super("Phyrexian Minion Token", "X/X black Phyrexian Minion creature token");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.PHYREXIAN);
        subtype.add(SubType.MINION);
        color.setBlack(true);
        power = new MageInt(0);
        toughness = new MageInt(0);
    }

    private PhyrexianMinionToken(final PhyrexianMinionToken token) {
        super(token);
    }

    public PhyrexianMinionToken copy() {
        return new PhyrexianMinionToken(this);
    }
}
