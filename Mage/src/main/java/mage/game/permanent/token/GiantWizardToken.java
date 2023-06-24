package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

public final class GiantWizardToken extends TokenImpl {

    public GiantWizardToken() {
        super("Giant Wizard Token", "4/4 blue Giant Wizard creature token");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add(SubType.GIANT);
        subtype.add(SubType.WIZARD);
        power = new MageInt(4);
        toughness = new MageInt(4);
    }

    public GiantWizardToken(final GiantWizardToken token) {
        super(token);
    }

    public GiantWizardToken copy() {
        return new GiantWizardToken(this);
    }
}
