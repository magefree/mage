package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 *
 * @author Loki
 */
public final class MerfolkWizardToken extends TokenImpl {

    public MerfolkWizardToken() {
        super("Merfolk Wizard Token", "1/1 blue Merfolk Wizard creature token");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add(SubType.MERFOLK);
        subtype.add(SubType.WIZARD);
        power = new MageInt(1);
        toughness = new MageInt(1);

        availableImageSetCodes = Arrays.asList("LRW");
    }

    public MerfolkWizardToken(final MerfolkWizardToken token) {
        super(token);
    }

    public MerfolkWizardToken copy() {
        return new MerfolkWizardToken(this);
    }
}
