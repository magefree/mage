package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author muz
 */
public final class ElementalXXFlyingHasteToken extends TokenImpl {

    public ElementalXXFlyingHasteToken(int xValue) {
        super("Elemental Token", "X/X blue and red Elemental creature token with flying and haste");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        color.setBlue(true);
        subtype.add(SubType.ELEMENTAL);
        power = new MageInt(xValue);
        toughness = new MageInt(xValue);
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(HasteAbility.getInstance());
    }

    private ElementalXXFlyingHasteToken(final ElementalXXFlyingHasteToken token) {
        super(token);
    }

    @Override
    public ElementalXXFlyingHasteToken copy() {
        return new ElementalXXFlyingHasteToken(this);
    }
}
