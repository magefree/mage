package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.mana.GreenManaAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class ElfDruidToken extends TokenImpl {

    public ElfDruidToken() {
        super("Elf Druid Token", "1/1 green Elf Druid creature token with \"{T}: Add {G}.\"");
        this.cardType.add(CardType.CREATURE);
        this.color.setGreen(true);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {T}: Add {G}.
        this.addAbility(new GreenManaAbility());
    }

    public ElfDruidToken(final ElfDruidToken token) {
        super(token);
    }

    public ElfDruidToken copy() {
        return new ElfDruidToken(this);
    }
}
