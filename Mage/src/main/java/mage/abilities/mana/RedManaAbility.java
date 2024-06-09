

package mage.abilities.mana;

import mage.Mana;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.constants.ColoredManaSymbol;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class RedManaAbility extends BasicManaAbility {

    public RedManaAbility() {
        super(new BasicManaEffect(Mana.RedMana(1)));
        this.netMana.add(new Mana(ColoredManaSymbol.R));
    }

    private RedManaAbility(final RedManaAbility ability) {
        super(ability);
    }

    @Override
    public RedManaAbility copy() {
        return new RedManaAbility(this);
    }

}
