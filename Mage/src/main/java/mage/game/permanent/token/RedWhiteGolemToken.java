package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author weirddan455
 */
public class RedWhiteGolemToken extends TokenImpl {

    public RedWhiteGolemToken() {
        super("Golem Token", "4/4 red and white Golem artifact creature token");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.GOLEM);
        color.setRed(true);
        color.setWhite(true);
        power = new MageInt(4);
        toughness = new MageInt(4);
    }

    private RedWhiteGolemToken(final RedWhiteGolemToken token) {
        super(token);
    }

    public RedWhiteGolemToken copy() {
        return new RedWhiteGolemToken(this);
    }
}
