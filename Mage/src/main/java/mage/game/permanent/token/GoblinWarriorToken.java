package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class GoblinWarriorToken extends TokenImpl {

    public GoblinWarriorToken() {
        super("Goblin Warrior Token", "1/1 red and green Goblin Warrior creature token");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        color.setGreen(true);
        subtype.add(SubType.GOBLIN);
        subtype.add(SubType.WARRIOR);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    public GoblinWarriorToken(final GoblinWarriorToken token) {
        super(token);
    }

    public GoblinWarriorToken copy() {
        return new GoblinWarriorToken(this);
    }
}
