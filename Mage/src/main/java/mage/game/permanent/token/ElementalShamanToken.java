package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author Styxo
 */
public final class ElementalShamanToken extends TokenImpl {

    public ElementalShamanToken(boolean withHaste) {
        this();

        if (withHaste) {
            addAbility(HasteAbility.getInstance());
            description = description + " with haste";
        }
    }

    public ElementalShamanToken() {
        super("Elemental Shaman Token", "3/1 red Elemental Shaman creature token");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.ELEMENTAL);
        subtype.add(SubType.SHAMAN);
        power = new MageInt(3);
        toughness = new MageInt(1);
    }

    public ElementalShamanToken(final ElementalShamanToken token) {
        super(token);
    }

    public ElementalShamanToken copy() {
        return new ElementalShamanToken(this);
    }
}
