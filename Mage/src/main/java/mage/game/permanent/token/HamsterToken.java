package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class HamsterToken extends TokenImpl {

    public HamsterToken() {
        super("Hamster Token", "1/1 red Hamster creature token");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.HAMSTER);
        color.setRed(true);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    private HamsterToken(final HamsterToken token) {
        super(token);
    }

    public HamsterToken copy() {
        return new HamsterToken(this);
    }
}
