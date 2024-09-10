package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class UginTheIneffableToken extends TokenImpl {

    public UginTheIneffableToken() {
        super("Spirit Token", "2/2 colorless Spirit creature token");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.SPIRIT);
        power = new MageInt(2);
        toughness = new MageInt(2);
    }

    private UginTheIneffableToken(final UginTheIneffableToken token) {
        super(token);
    }

    @Override
    public UginTheIneffableToken copy() {
        return new UginTheIneffableToken(this);
    }
}
