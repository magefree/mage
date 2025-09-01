package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class ElementalAllColorsToken extends TokenImpl {

    public ElementalAllColorsToken() {
        super("Elemental Token", "2/2 Elemental creature token that's all colors");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        color.setBlue(true);
        color.setBlack(true);
        color.setRed(true);
        color.setGreen(true);
        subtype.add(SubType.ELEMENTAL);
        power = new MageInt(2);
        toughness = new MageInt(2);
    }

    private ElementalAllColorsToken(final ElementalAllColorsToken token) {
        super(token);
    }

    @Override
    public ElementalAllColorsToken copy() {
        return new ElementalAllColorsToken(this);
    }
}
