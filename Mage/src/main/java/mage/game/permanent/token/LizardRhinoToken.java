package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author ChesseTheWasp
 */
public final class LizardRhinoToken extends TokenImpl {

    public LizardRhinoToken() {
        super("Lizard Rhino Token", "1/1 Red Lizard Rhino");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.LIZARD);
        subtype.add(SubType.RHINO);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    private LizardRhinoToken(final LizardRhinoToken token) {
        super(token);
    }

    public LizardRhinoToken copy() {
        return new LizardRhinoToken(this);
    }
}
