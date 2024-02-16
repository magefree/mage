

package mage.abilities.mana;

import mage.Mana;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.constants.ColoredManaSymbol;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class BlueManaAbility extends BasicManaAbility {

    public BlueManaAbility() {
        super(new BasicManaEffect(Mana.BlueMana(1)));
        this.netMana.add(new Mana(ColoredManaSymbol.U));
    }

    private BlueManaAbility(final BlueManaAbility ability) {
        super(ability);
    }

    @Override
    public BlueManaAbility copy() {
        return new BlueManaAbility(this);
    }

}
