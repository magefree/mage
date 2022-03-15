

package mage.game.permanent.token;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

/**
 *
 * @author spjspj
 */
public final class ZombieWizardToken extends TokenImpl {

    public ZombieWizardToken() {
        super("Zombie Wizard Token", "1/1 blue and black Zombie Wizard creature token");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        color.setBlack(true);
        subtype.add(SubType.ZOMBIE);
        subtype.add(SubType.WIZARD);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    public ZombieWizardToken(final ZombieWizardToken token) {
        super(token);
    }

    public ZombieWizardToken copy() {
        return new ZombieWizardToken(this);
    }
}
