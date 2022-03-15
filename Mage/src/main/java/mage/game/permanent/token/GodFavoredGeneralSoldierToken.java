
package mage.game.permanent.token;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

/**
 *
 * @author spjspj
 */
public final class GodFavoredGeneralSoldierToken extends TokenImpl {

    public GodFavoredGeneralSoldierToken() {
        super("Soldier Token", "1/1 white Soldier enchantment creature token");
        cardType.add(CardType.ENCHANTMENT);
        cardType.add(CardType.CREATURE);
        color.setWhite(true);

        subtype.add(SubType.SOLDIER);
        power = new MageInt(1);
        toughness = new MageInt(1);
        this.setOriginalExpansionSetCode("BNG");
    }

    public GodFavoredGeneralSoldierToken(final GodFavoredGeneralSoldierToken token) {
        super(token);
    }

    public GodFavoredGeneralSoldierToken copy() {
        return new GodFavoredGeneralSoldierToken(this);
    }
}
