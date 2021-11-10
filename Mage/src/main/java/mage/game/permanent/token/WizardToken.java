package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

public final class WizardToken extends TokenImpl {

    public WizardToken() {
        this("WAR");
    }

    public WizardToken(String setCode) {
        super("Wizard Token", "2/2 blue Wizard creature token");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.WIZARD);
        color.setBlue(true);
        power = new MageInt(2);
        toughness = new MageInt(2);

        setOriginalExpansionSetCode(setCode);
    }

    private WizardToken(final WizardToken token) {
        super(token);
    }

    public WizardToken copy() {
        return new WizardToken(this);
    }
}
