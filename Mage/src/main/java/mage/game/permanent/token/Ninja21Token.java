package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author ciaccona007
 */
public final class Ninja21Token extends TokenImpl {

    public Ninja21Token() {
        super("Ninja Token", "2/1 blue Ninja creature token");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add(SubType.NINJA);
        power = new MageInt(2);
        toughness = new MageInt(1);
    }

    private Ninja21Token(final Ninja21Token token) {
        super(token);
    }

    public Ninja21Token copy() {
        return new Ninja21Token(this);
    }
}
