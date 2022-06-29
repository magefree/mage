
package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 *
 * @author spjspj
 */
public final class Elemental11HasteToken extends TokenImpl {

    public Elemental11HasteToken() {
        super("Elemental Token", "1/1 red Elemental creature token with haste");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.ELEMENTAL);
        color.setRed(true);
        power = new MageInt(1);
        toughness = new MageInt(1);
        addAbility(HasteAbility.getInstance());

        availableImageSetCodes = Arrays.asList("SHM");
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);

        if (getOriginalExpansionSetCode().equals("SHM")) {
            this.setTokenType(2);
        }
    }

    public Elemental11HasteToken(final Elemental11HasteToken token) {
        super(token);
    }

    public Elemental11HasteToken copy() {
        return new Elemental11HasteToken(this);
    }
}
