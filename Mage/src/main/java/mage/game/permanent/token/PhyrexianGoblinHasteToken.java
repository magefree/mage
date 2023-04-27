package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author TheElk801
 */
public final class PhyrexianGoblinHasteToken extends TokenImpl {

    public PhyrexianGoblinHasteToken() {
        super("Phyrexian Goblin Token", "1/1 red Phyrexian Goblin creature token with haste");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.PHYREXIAN);
        subtype.add(SubType.GOBLIN);
        color.setRed(true);
        power = new MageInt(1);
        toughness = new MageInt(1);

        addAbility(HasteAbility.getInstance());

        availableImageSetCodes = Arrays.asList("NPH");
    }

    public PhyrexianGoblinHasteToken(final PhyrexianGoblinHasteToken token) {
        super(token);
    }

    public PhyrexianGoblinHasteToken copy() {
        return new PhyrexianGoblinHasteToken(this);
    }
}
