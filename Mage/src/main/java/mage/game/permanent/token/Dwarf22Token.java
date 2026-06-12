package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author muz
 */
public final class Dwarf22Token extends TokenImpl {

    public Dwarf22Token() {
        super("Dwarf Token", "2/2 red Dwarf creature token");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.DWARF);
        power = new MageInt(2);
        toughness = new MageInt(2);
    }

    private Dwarf22Token(final Dwarf22Token token) {
        super(token);
    }

    public Dwarf22Token copy() {
        return new Dwarf22Token(this);
    }
}
