package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

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

        availableImageSetCodes = Arrays.asList("C14", "C16", "EVG", "EMA", "LRW", "MOR", "ORI", "SHM", "M19", "CMR", "KHM");
    }

    public ElfWarriorToken(final ElfWarriorToken token) {
        super(token);
    }

    @Override
    public ElfWarriorToken copy() {
        return new ElfWarriorToken(this);
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);

        if (getOriginalExpansionSetCode().equals("SHM")) {
            this.setTokenType(1);
        }
    }
}
