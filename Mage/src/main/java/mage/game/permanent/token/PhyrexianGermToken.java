package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class PhyrexianGermToken extends TokenImpl {

    public PhyrexianGermToken() {
        super("Phyrexian Germ Token", "0/0 black Phyrexian Germ creature token");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.PHYREXIAN);
        subtype.add(SubType.GERM);
        power = new MageInt(0);
        toughness = new MageInt(0);
    }

    public PhyrexianGermToken(final PhyrexianGermToken token) {
        super(token);
    }

    @Override
    public PhyrexianGermToken copy() {
        return new PhyrexianGermToken(this);
    }
}
