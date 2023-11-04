package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author Susucr
 */
public final class Dinosaur31Token extends TokenImpl {

    public Dinosaur31Token() {
        super("Dinosaur Token", "3/1 red Dinosaur creature token");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.DINOSAUR);
        power = new MageInt(3);
        toughness = new MageInt(1);
    }

    protected Dinosaur31Token(final Dinosaur31Token token) {
        super(token);
    }

    public Dinosaur31Token copy() {
        return new Dinosaur31Token(this);
    }
}
