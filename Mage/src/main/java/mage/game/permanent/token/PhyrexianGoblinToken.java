package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author TheElk801
 */
public final class PhyrexianGoblinToken extends TokenImpl {

    public PhyrexianGoblinToken() {
        super("Phyrexian Goblin Token", "1/1 red Phyrexian Goblin creature token");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.PHYREXIAN);
        subtype.add(SubType.GOBLIN);
        color.setRed(true);
        power = new MageInt(1);
        toughness = new MageInt(1);

        availableImageSetCodes = Arrays.asList("ONE");
    }

    public PhyrexianGoblinToken(final PhyrexianGoblinToken token) {
        super(token);
    }

    public PhyrexianGoblinToken copy() {
        return new PhyrexianGoblinToken(this);
    }
}
