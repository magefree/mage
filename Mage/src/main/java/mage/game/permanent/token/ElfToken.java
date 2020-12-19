package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author Loki
 */
public final class ElfToken extends TokenImpl {

    public ElfToken() {
        super("Elf Warrior", "1/1 green Elf Warrior creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.ELF);
        subtype.add(SubType.WARRIOR);
        power = new MageInt(1);
        toughness = new MageInt(1);

        availableImageSetCodes = Arrays.asList("C13", "C14", "EVG", "EMA", "LRW", "MOR", "ORI", "SHM", "M19", "CMR");
    }

    public ElfToken(final ElfToken token) {
        super(token);
    }

    @Override
    public ElfToken copy() {
        return new ElfToken(this);
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);
    }
}
