
package mage.game.permanent.token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Styxo
 */
public final class ElementalShamanToken extends TokenImpl {

    public ElementalShamanToken(boolean withHaste) {
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

        availableImageSetCodes = Arrays.asList("C15", "JVC", "DD2", "LRW", "CM2");
    }

    public ElementalShamanToken(final ElementalShamanToken token) {
        super(token);
    }

    public ElementalShamanToken copy() {
        return new ElementalShamanToken(this);
    }
}
