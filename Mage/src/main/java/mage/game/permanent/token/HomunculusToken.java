

package mage.game.permanent.token;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

/**
 * @author spjspj
 */
public final class HomunculusToken extends TokenImpl {

    public HomunculusToken() {
        super("Homunculus Token", "0/1 blue Homunculus artifact creature token");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add(SubType.HOMUNCULUS);
        power = new MageInt(0);
        toughness = new MageInt(1);
    }

    protected HomunculusToken(final HomunculusToken token) {
        super(token);
    }

    public HomunculusToken copy() {
        return new HomunculusToken(this);
    }

}
