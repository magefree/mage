package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author PurpleCrowbar
 */
public final class Soldier22Token extends TokenImpl {

    public Soldier22Token() {
        super("Soldier Token", "2/2 white Soldier creature token");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.SOLDIER);
        power = new MageInt(2);
        toughness = new MageInt(2);
    }

    private Soldier22Token(final Soldier22Token token) {
        super(token);
    }

    @Override
    public Soldier22Token copy() {
        return new Soldier22Token(this);
    }
}
