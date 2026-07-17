package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class NinjaTurtleSpiritToken extends TokenImpl {

    public NinjaTurtleSpiritToken() {
        super("Ninja Turtle Spirit Token", "1/1 white Ninja Turtle Spirit creature token");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.NINJA);
        subtype.add(SubType.TURTLE);
        subtype.add(SubType.SPIRIT);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    private NinjaTurtleSpiritToken(final NinjaTurtleSpiritToken token) {
        super(token);
    }

    public NinjaTurtleSpiritToken copy() {
        return new NinjaTurtleSpiritToken(this);
    }
}
