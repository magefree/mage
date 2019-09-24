package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author jmharmon
 */

public final class WizardToken2 extends TokenImpl {

    public WizardToken2() {
        super("Wizard", "1/1 blue Wizard creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add(SubType.WIZARD);
        power = new MageInt(1);
        toughness = new MageInt(1);
        addAbility(FlyingAbility.getInstance());
    }

    public WizardToken2(final WizardToken2 token) {
        super(token);
    }

    public WizardToken2 copy() {
        return new WizardToken2(this);
    }
}
