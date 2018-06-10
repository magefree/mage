
package mage.game.permanent.token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.mana.GreenManaAbility;

/**
 *
 * @author spjspj
 */
public final class FreyaliseLlanowarsFuryToken extends TokenImpl {

    final static private List<String> tokenImageSets = new ArrayList<>();

    static {
        tokenImageSets.addAll(Arrays.asList("C14", "CMA"));
    }

    public FreyaliseLlanowarsFuryToken() {
        this(null, 0);
    }

    public FreyaliseLlanowarsFuryToken(String setCode) {
        this(setCode, 0);
    }

    public FreyaliseLlanowarsFuryToken(String setCode, int tokenType) {
        super("Elf Druid", "1/1 green Elf Druid creature token with \"{T}: Add {G}.\"");
        availableImageSetCodes = tokenImageSets;
        setOriginalExpansionSetCode(setCode);
        this.cardType.add(CardType.CREATURE);
        this.color = ObjectColor.GREEN;
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {T}: Add {G}.
        this.addAbility(new GreenManaAbility());
    }

    public FreyaliseLlanowarsFuryToken(final FreyaliseLlanowarsFuryToken token) {
        super(token);
    }

    public FreyaliseLlanowarsFuryToken copy() {
        return new FreyaliseLlanowarsFuryToken(this);
    }
}
