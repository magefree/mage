package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author mschatz
 */
public final class ElfGreenToken extends TokenImpl {

    public ElfGreenToken(int power_val, int toughness_val) {
        super("ElfGreen", power_val + "/" + toughness_val + " green Elf creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.ELF );
        power = new MageInt(power_val);
        toughness = new MageInt(toughness_val);
    }

    public ElfGreenToken() {
        this(2, 2);
    }

    public ElfGreenToken(final ElfGreenToken token) {
        super(token);
    }

    public ElfGreenToken copy() {
        return new ElfGreenToken(this);
    }
}
