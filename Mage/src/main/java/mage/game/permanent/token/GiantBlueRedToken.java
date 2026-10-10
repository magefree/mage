package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author muz
 */
public final class GiantBlueRedToken extends TokenImpl {

    public GiantBlueRedToken() {
        super("Giant Token", "4/4 blue and red Giant creature token");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.GIANT);
        color.setBlue(true);
        color.setRed(true);
        power = new MageInt(4);
        toughness = new MageInt(4);
    }

    private GiantBlueRedToken(final GiantBlueRedToken token) {
        super(token);
    }

    public GiantBlueRedToken copy() {
        return new GiantBlueRedToken(this);
    }
}
