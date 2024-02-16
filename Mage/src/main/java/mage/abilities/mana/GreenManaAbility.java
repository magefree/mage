

package mage.abilities.mana;

import mage.Mana;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.constants.ColoredManaSymbol;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class GreenManaAbility extends BasicManaAbility {

    public GreenManaAbility() {
        super(new BasicManaEffect(Mana.GreenMana(1)));
        this.netMana.add(new Mana(ColoredManaSymbol.G));
    }

    private GreenManaAbility(final GreenManaAbility ability) {
        super(ability);
    }

    @Override
    public GreenManaAbility copy() {
        return new GreenManaAbility(this);
    }

}
