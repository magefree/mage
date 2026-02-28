package mage.game.permanent.token.custom;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.game.permanent.token.TokenImpl;

/**
 * Builder for blueprints to pass into flip effects etc.
 *
 * @author muz
 */
public final class EnchantmentToken extends TokenImpl {

    public EnchantmentToken(String name, boolean isLegendary) {
        super(name, "");
        this.cardType.add(CardType.ENCHANTMENT);
        if (isLegendary) {
            this.supertype.add(SuperType.LEGENDARY);
        }
    }

    public EnchantmentToken withAbility(Ability ability) {
        this.addAbility(ability);
        return this;
    }

    public EnchantmentToken withColor(String extraColors) {
        ObjectColor extraColorsList = new ObjectColor(extraColors);
        this.getColor(null).addColor(extraColorsList);
        return this;
    }

    private EnchantmentToken(final EnchantmentToken token) {
        super(token);
    }

    public EnchantmentToken copy() {
        return new EnchantmentToken(this);
   }
}
