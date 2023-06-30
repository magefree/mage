package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class HalflingToken extends TokenImpl {

    public HalflingToken() {
        super("Halfling Token", "1/1 white Halfling creature token");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.HALFLING);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    public HalflingToken(final HalflingToken token) {
        super(token);
    }

    @Override
    public HalflingToken copy() {
        return new HalflingToken(this);
    }
}
