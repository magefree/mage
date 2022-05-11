package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.ProwessAbility;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author TheElk801
 */
public final class GoblinWizardToken extends TokenImpl {

    public GoblinWizardToken() {
        super("Goblin Wizard Token", "1/1 red Goblin Wizard creature token with prowess");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.GOBLIN);
        subtype.add(SubType.WIZARD);
        color.setRed(true);
        power = new MageInt(1);
        toughness = new MageInt(1);
        this.addAbility(new ProwessAbility());

        availableImageSetCodes.addAll(Arrays.asList("M21"));
    }

    private GoblinWizardToken(final GoblinWizardToken token) {
        super(token);
    }

    public GoblinWizardToken copy() {
        return new GoblinWizardToken(this);
    }
}
