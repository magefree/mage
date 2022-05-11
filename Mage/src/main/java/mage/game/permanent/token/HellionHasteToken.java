
package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author spjspj
 */
public final class HellionHasteToken extends TokenImpl {

    public HellionHasteToken() {
        super("Hellion Token", "4/4 red Hellion creature token with haste");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.HELLION);
        power = new MageInt(4);
        toughness = new MageInt(4);
        addAbility(HasteAbility.getInstance());
    }
    public HellionHasteToken(final HellionHasteToken token) {
        super(token);
    }

    public HellionHasteToken copy() {
        return new HellionHasteToken(this);
    }
}
