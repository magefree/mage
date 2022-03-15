
package mage.game.permanent.token;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

/**
 *
 * @author spjspj
 */
public final class LizardToken extends TokenImpl {

    public LizardToken() {
        super("Lizard Token", "2/2 green Lizard creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.LIZARD);
        power = new MageInt(2);
        toughness = new MageInt(2);
    }

    public LizardToken(final LizardToken token) {
        super(token);
    }

    public LizardToken copy() {
        return new LizardToken(this);
    }
}
