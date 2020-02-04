

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.mana.GreenManaAbility;

/**
 *
 * @author spjspj
 */
public final class LlanowarElvesToken extends TokenImpl {

    public LlanowarElvesToken() {
        super("Llanowar Elves", "1/1 green Elf Druid creature token named Llanowar Elves with \"{T}: Add {G}.\"");
        this.setOriginalExpansionSetCode("FUT");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.ELF);
        subtype.add(SubType.DRUID);
        power = new MageInt(1);
        toughness = new MageInt(1);

        this.addAbility(new GreenManaAbility());
    }

    public LlanowarElvesToken(final LlanowarElvesToken token) {
        super(token);
    }

    public LlanowarElvesToken copy() {
        return new LlanowarElvesToken(this);
    }
}
