

package mage.abilities.mana;

import mage.Mana;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.constants.ColoredManaSymbol;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class WhiteManaAbility extends BasicManaAbility {

    public WhiteManaAbility() {
        super(new BasicManaEffect(Mana.WhiteMana(1)));
        this.netMana.add(new Mana(ColoredManaSymbol.W));
    }

    private WhiteManaAbility(final WhiteManaAbility ability) {
        super(ability);
    }

    @Override
    public WhiteManaAbility copy() {
        return new WhiteManaAbility(this);
    }

}
