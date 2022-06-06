

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

import java.util.Arrays;

/**
 *
 * @author spjspj
 */
public final class OgreToken extends TokenImpl {

    public OgreToken() {
        super("Ogre Token", "3/3 red Ogre creature");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.OGRE);
        power = new MageInt(3);
        toughness = new MageInt(3);

        availableImageSetCodes = Arrays.asList("WWK", "C16", "NCC", "CLB");
    }

    public OgreToken(final OgreToken token) {
        super(token);
    }

    public OgreToken copy() {
        return new OgreToken(this);
    }
}
