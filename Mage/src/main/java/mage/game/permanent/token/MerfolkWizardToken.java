package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;

/**
 *
 * @author Loki
 */
public class MerfolkWizardToken extends Token {

    public MerfolkWizardToken() {
        super("Merfolk Wizard", "1/1 blue Merfolk Wizard creature token");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add("Merfolk");
        subtype.add("Wizard");
        power = new MageInt(1);
        toughness = new MageInt(1);
    }
}
