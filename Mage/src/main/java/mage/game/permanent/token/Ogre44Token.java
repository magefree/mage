package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author androosss
 */
public final class Ogre44Token extends TokenImpl {

    public Ogre44Token() {
        super("Ogre Token", "4/4 red Ogre creature token");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.OGRE);
        power = new MageInt(4);
        toughness = new MageInt(4);
    }

    private Ogre44Token(final Ogre44Token token) {
        super(token);
    }

    public Ogre44Token copy() {
        return new Ogre44Token(this);
    }
}
