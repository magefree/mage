package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class PhyrexianToken extends TokenImpl {

    public PhyrexianToken() {
        super("Phyrexian Token", "2/2 black Phyrexian creature token");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.PHYREXIAN);
        power = new MageInt(2);
        toughness = new MageInt(2);
    }

    public PhyrexianToken(final PhyrexianToken token) {
        super(token);
    }

    @Override
    public PhyrexianToken copy() {
        return new PhyrexianToken(this);
    }
}
