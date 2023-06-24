package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class GiantWarriorToken extends TokenImpl {

    public GiantWarriorToken() {
        super("Giant Warrior Token", "5/5 white Giant Warrior creature token");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.GIANT);
        subtype.add(SubType.WARRIOR);
        power = new MageInt(5);
        toughness = new MageInt(5);
    }

    public GiantWarriorToken(final GiantWarriorToken token) {
        super(token);
    }

    public GiantWarriorToken copy() {
        return new GiantWarriorToken(this);
    }
}
