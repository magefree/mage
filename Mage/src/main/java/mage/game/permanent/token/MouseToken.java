package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class MouseToken extends TokenImpl {

    public MouseToken() {
        super("Mouse Token", "1/1 white Mouse creature token");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.MOUSE);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    private MouseToken(final MouseToken token) {
        super(token);
    }

    @Override
    public MouseToken copy() {
        return new MouseToken(this);
    }
}
