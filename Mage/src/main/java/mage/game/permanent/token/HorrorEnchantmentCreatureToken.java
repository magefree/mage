package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author Cguy7777
 */
public class HorrorEnchantmentCreatureToken extends TokenImpl {

    public HorrorEnchantmentCreatureToken() {
        super("Horror Token", "2/2 black Horror enchantment creature token");
        cardType.add(CardType.ENCHANTMENT);
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.HORROR);
        power = new MageInt(2);
        toughness = new MageInt(2);
    }

    private HorrorEnchantmentCreatureToken(final HorrorEnchantmentCreatureToken token) {
        super(token);
    }

    @Override
    public HorrorEnchantmentCreatureToken copy() {
        return new HorrorEnchantmentCreatureToken(this);
    }
}
