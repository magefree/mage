package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

public final class WizardToken extends TokenImpl {

    public WizardToken() {
        super("Wizard", "2/2 blue Wizard creature token");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.WIZARD);
        color.setBlue(true);
        power = new MageInt(2);
        toughness = new MageInt(2);
    }

    private WizardToken(final WizardToken token) {
        super(token);
    }

    public WizardToken copy() {
        return new WizardToken(this);
    }
}
