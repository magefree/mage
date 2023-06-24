package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author Loki
 */
public final class ElfWarriorToken extends TokenImpl {

    public ElfWarriorToken() {
        super("Elf Warrior Token", "1/1 green Elf Warrior creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.ELF);
        subtype.add(SubType.WARRIOR);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    public ElfWarriorToken(final ElfWarriorToken token) {
        super(token);
    }

    @Override
    public ElfWarriorToken copy() {
        return new ElfWarriorToken(this);
    }
}
