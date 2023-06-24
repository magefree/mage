package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class GreenWhiteElfWarriorToken extends TokenImpl {

    public GreenWhiteElfWarriorToken() {
        super("Elf Warrior Token", "1/1 green and white Elf Warrior creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        color.setWhite(true);
        subtype.add(SubType.ELF);
        subtype.add(SubType.WARRIOR);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    public GreenWhiteElfWarriorToken(final GreenWhiteElfWarriorToken token) {
        super(token);
    }

    public GreenWhiteElfWarriorToken copy() {
        return new GreenWhiteElfWarriorToken(this);
    }
}
