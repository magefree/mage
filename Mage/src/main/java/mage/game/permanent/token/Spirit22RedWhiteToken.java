package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class Spirit22RedWhiteToken extends TokenImpl {

    public Spirit22RedWhiteToken() {
        super("Spirit Token", "2/2 red and white Spirit creature token");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        color.setWhite(true);
        subtype.add(SubType.SPIRIT);
        power = new MageInt(2);
        toughness = new MageInt(2);
    }

    private Spirit22RedWhiteToken(final Spirit22RedWhiteToken token) {
        super(token);
    }

    @Override
    public Spirit22RedWhiteToken copy() {
        return new Spirit22RedWhiteToken(this);
    }
}
