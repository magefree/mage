package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

public final class DwarfBerserkerToken extends TokenImpl {

    public DwarfBerserkerToken() {
        super("Dwarf Berserker Token", "2/1 red Dwarf Berserker creature token");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.DWARF);
        subtype.add(SubType.BERSERKER);
        power = new MageInt(2);
        toughness = new MageInt(1);
    }

    public DwarfBerserkerToken(final DwarfBerserkerToken token) {
        super(token);
    }

    public DwarfBerserkerToken copy() {
        return new DwarfBerserkerToken(this);
    }
}
