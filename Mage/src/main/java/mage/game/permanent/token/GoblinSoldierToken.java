package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

import java.util.Arrays;

/**
 *
 * @author spjspj
 */
public final class GoblinSoldierToken extends TokenImpl {

    public GoblinSoldierToken() {
        super("Goblin Soldier Token", "1/1 red and white Goblin Soldier creature token");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        color.setWhite(true);
        subtype.add(SubType.GOBLIN);
        subtype.add(SubType.SOLDIER);
        power = new MageInt(1);
        toughness = new MageInt(1);

        availableImageSetCodes = Arrays.asList("EVE", "EMA");
    }

    public GoblinSoldierToken(final GoblinSoldierToken token) {
        super(token);
    }

    public GoblinSoldierToken copy() {
        return new GoblinSoldierToken(this);
    }
}
