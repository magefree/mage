package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class Ninja11Token extends TokenImpl {

    public Ninja11Token() {
        super("Ninja Token", "1/1 black Ninja creature token");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.NINJA);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    private Ninja11Token(final Ninja11Token token) {
        super(token);
    }

    public Ninja11Token copy() {
        return new Ninja11Token(this);
    }
}
