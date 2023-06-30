package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class HazezonTamarSandWarriorToken extends TokenImpl {

    public HazezonTamarSandWarriorToken() {
        super("Sand Warrior Token", "1/1 red, green, and white Sand Warrior creature tokens");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        color.setGreen(true);
        color.setWhite(true);
        subtype.add(SubType.SAND);
        subtype.add(SubType.WARRIOR);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    public HazezonTamarSandWarriorToken(final HazezonTamarSandWarriorToken token) {
        super(token);
    }

    public HazezonTamarSandWarriorToken copy() {
        return new HazezonTamarSandWarriorToken(this);
    }
}
