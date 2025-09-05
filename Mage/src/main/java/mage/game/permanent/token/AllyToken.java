package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class AllyToken extends TokenImpl {

    public AllyToken() {
        super("Ally Token", "1/1 white Ally creature token");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.ALLY);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    private AllyToken(final AllyToken token) {
        super(token);
    }

    public AllyToken copy() {
        return new AllyToken(this);
    }

}
