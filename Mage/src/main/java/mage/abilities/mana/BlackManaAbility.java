

package mage.abilities.mana;

import mage.Mana;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.constants.ColoredManaSymbol;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class BlackManaAbility extends BasicManaAbility {

    public BlackManaAbility() {
        super(new BasicManaEffect(Mana.BlackMana(1)));
        this.netMana.add(new Mana(ColoredManaSymbol.B));
    }

    private BlackManaAbility(final BlackManaAbility ability) {
        super(ability);
    }

    @Override
    public BlackManaAbility copy() {
        return new BlackManaAbility(this);
    }

}
