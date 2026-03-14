package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class DoombotToken extends TokenImpl {

    public DoombotToken() {
        super("Doombot", "3/3 colorless Robot Villain artifact creature token named Doombot");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.ROBOT);
        subtype.add(SubType.VILLAIN);
        power = new MageInt(3);
        toughness = new MageInt(3);
    }

    private DoombotToken(final DoombotToken token) {
        super(token);
    }

    public DoombotToken copy() {
        return new DoombotToken(this);
    }
}
