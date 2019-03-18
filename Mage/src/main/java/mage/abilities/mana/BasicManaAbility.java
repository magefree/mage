

package mage.abilities.mana;

import mage.constants.Zone;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.ManaEffect;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public abstract class BasicManaAbility extends ActivatedManaAbilityImpl {

    public BasicManaAbility(ManaEffect effect) {
        super(Zone.BATTLEFIELD, effect, new TapSourceCost());
    }

    public BasicManaAbility(BasicManaAbility ability) {
        super(ability);
    }

}
