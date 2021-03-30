package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class LoreholdToken extends TokenImpl {

    public LoreholdToken() {
        super("Spirit", "3/2 red and white Spirit creature token");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        color.setWhite(true);
        subtype.add(SubType.SPIRIT);
        power = new MageInt(3);
        toughness = new MageInt(2);
    }

    private LoreholdToken(final LoreholdToken token) {
        super(token);
    }

    @Override
    public LoreholdToken copy() {
        return new LoreholdToken(this);
    }
}
