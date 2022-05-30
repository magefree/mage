package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;
import java.util.Arrays;

/**
 * @author TheElk801
 */
public final class OgreWarriorToken extends TokenImpl {

    public OgreWarriorToken() {
        super("Ogre Warrior Token", "4/3 black Ogre Warrior creature token");
        color.setBlack(true);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.OGRE);
        subtype.add(SubType.WARRIOR);
        power = new MageInt(4);
        toughness = new MageInt(3);

        availableImageSetCodes = Arrays.asList("SNC");
    }

    public OgreWarriorToken(final OgreWarriorToken token) {
        super(token);
    }

    public OgreWarriorToken copy() {
        return new OgreWarriorToken(this);
    }
}
