package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.VigilanceAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class ElfKnightToken extends TokenImpl {

    public ElfKnightToken() {
        super("Elf Knight Token", "2/2 green and white Elf Knight creature token with vigilance");
        this.setExpansionSetCodeForImage("GRN");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        color.setWhite(true);
        subtype.add(SubType.ELF);
        subtype.add(SubType.KNIGHT);
        power = new MageInt(2);
        toughness = new MageInt(2);
        this.addAbility(VigilanceAbility.getInstance());
    }

    public ElfKnightToken(final ElfKnightToken token) {
        super(token);
    }

    public ElfKnightToken copy() {
        return new ElfKnightToken(this);
    }

}
