
package mage.game.permanent.token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class ElfToken extends TokenImpl {

    final static private List<String> tokenImageSets = new ArrayList<>();

    static {
        tokenImageSets.addAll(Arrays.asList("C14", "SHM", "EVG", "LRW", "ORI"));
    }

    public ElfToken() {
        super("Elf Warrior", "1/1 green Elf Warrior creature token");
        availableImageSetCodes = tokenImageSets;
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.ELF);
        subtype.add(SubType.WARRIOR);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);
        if (getOriginalExpansionSetCode().equals("SHM")) {
            this.setTokenType(1);
        }
    }

    public ElfToken(final ElfToken token) {
        super(token);
    }

    @Override
    public ElfToken copy() {
        return new ElfToken(this); //To change body of generated methods, choose Tools | Templates.
    }
}
