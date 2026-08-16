package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author muz
 */
public final class Illusion11Token extends TokenImpl {

    public Illusion11Token() {
        super("Illusion Token", "1/1 blue Illusion creature token");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);

        subtype.add(SubType.ILLUSION);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    private Illusion11Token(final Illusion11Token token) {
        super(token);
    }

    public Illusion11Token copy() {
        return new Illusion11Token(this);
    }
}
