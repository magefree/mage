package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author magenoxx
 */
public final class ElementalHasteToken extends TokenImpl {

    public ElementalHasteToken() {
        super("Elemental Token", "3/1 red Elemental creature token with haste");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.ELEMENTAL);
        power = new MageInt(3);
        toughness = new MageInt(1);
        this.addAbility(HasteAbility.getInstance());
    }

    protected ElementalHasteToken(final ElementalHasteToken token) {
        super(token);
    }

    public ElementalHasteToken copy() {
        return new ElementalHasteToken(this);
    }
}
