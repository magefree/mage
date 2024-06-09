
package mage.abilities.mana;

import mage.Mana;
import mage.abilities.effects.mana.BasicManaEffect;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ColorlessManaAbility extends BasicManaAbility {

    public ColorlessManaAbility() {
        super(new BasicManaEffect(Mana.ColorlessMana(1)));
        this.netMana.add(Mana.ColorlessMana(1));
    }

    private ColorlessManaAbility(final ColorlessManaAbility ability) {
        super(ability);
    }

    @Override
    public ColorlessManaAbility copy() {
        return new ColorlessManaAbility(this);
    }
}
