package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author Susucr
 */
public final class Alien00Token extends TokenImpl {

    public Alien00Token() {
        super("Alien Token", "0/0 blue Alien creature token");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add(SubType.ALIEN);
        power = new MageInt(0);
        toughness = new MageInt(0);
    }

    private Alien00Token(final Alien00Token token) {
        super(token);
    }

    @Override
    public Alien00Token copy() {
        return new Alien00Token(this);
    }
}
