
package mage.game.permanent.token;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

/**
 *
 * @author spjspj
 */
public final class SubterraneanTremorsLizardToken extends TokenImpl {

    public SubterraneanTremorsLizardToken() {
        super("Lizard Token", "an 8/8 red Lizard creature token");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.LIZARD);
        power = new MageInt(8);
        toughness = new MageInt(8);
    }

    public SubterraneanTremorsLizardToken(final SubterraneanTremorsLizardToken token) {
        super(token);
    }

    public SubterraneanTremorsLizardToken copy() {
        return new SubterraneanTremorsLizardToken(this);
    }
}
