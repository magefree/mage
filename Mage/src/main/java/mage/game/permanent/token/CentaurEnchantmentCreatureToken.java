
package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class CentaurEnchantmentCreatureToken extends TokenImpl {

    public CentaurEnchantmentCreatureToken() {
        super("Centaur Token", "3/3 green Centaur enchantment creature token");
        cardType.add(CardType.ENCHANTMENT);
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.CENTAUR);
        power = new MageInt(3);
        toughness = new MageInt(3);
        this.setOriginalExpansionSetCode("BNG");
    }

    public CentaurEnchantmentCreatureToken(final CentaurEnchantmentCreatureToken token) {
        super(token);
    }

    public CentaurEnchantmentCreatureToken copy() {
        return new CentaurEnchantmentCreatureToken(this);
    }
}
