package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author ChesseTheWasp
 */
public final class InsectFishToken extends TokenImpl {

    public InsectFishToken() {
        super("Insect Fish Token", "1/1 Green Insect Fish");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.INSECT);
        subtype.add(SubType.FISH);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    private InsectFishToken(final InsectFishToken token) {
        super(token);
    }

    public InsectFishToken copy() {
        return new InsectFishToken(this);
    }
}
