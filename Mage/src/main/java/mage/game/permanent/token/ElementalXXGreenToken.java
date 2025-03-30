package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class ElementalXXGreenToken extends TokenImpl {

    public ElementalXXGreenToken() {
        this(1);
    }

    public ElementalXXGreenToken(int xValue) {
        super("Elemental Token", "X/X green Elemental creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.ELEMENTAL);
        power = new MageInt(xValue);
        toughness = new MageInt(xValue);
    }

    private ElementalXXGreenToken(final ElementalXXGreenToken token) {
        super(token);
    }

    public ElementalXXGreenToken copy() {
        return new ElementalXXGreenToken(this);
    }
}
