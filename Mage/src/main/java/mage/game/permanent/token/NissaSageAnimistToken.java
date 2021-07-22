
package mage.game.permanent.token;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

/**
 *
 * @author spjspj
 */
public final class NissaSageAnimistToken extends TokenImpl {

    public NissaSageAnimistToken() {
        super("Ashaya, the Awoken World", "Ashaya, the Awoken World, a legendary 4/4 green Elemental creature token");
        this.setOriginalExpansionSetCode("ORI");
        this.supertype.add(SuperType.LEGENDARY);
        this.getPower().modifyBaseValue(4);
        this.getToughness().modifyBaseValue(4);
        this.color.setGreen(true);
        this.subtype.add(SubType.ELEMENTAL);
        this.cardType.add(CardType.CREATURE);
    }

    public NissaSageAnimistToken(final NissaSageAnimistToken token) {
        super(token);
    }

    public NissaSageAnimistToken copy() {
        return new NissaSageAnimistToken(this);
    }
}
