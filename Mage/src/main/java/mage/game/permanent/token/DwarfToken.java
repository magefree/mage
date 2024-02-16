package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class DwarfToken extends TokenImpl {

    public DwarfToken() {
        super("Dwarf Token", "1/1 red Dwarf creature token");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.DWARF);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    private DwarfToken(final DwarfToken token) {
        super(token);
    }

    public DwarfToken copy() {
        return new DwarfToken(this);
    }
}
