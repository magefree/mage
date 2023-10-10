package mage.abilities.mana;

import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.mana.ManaEffect;
import mage.constants.Zone;

/**
 * @author BetaSteward_at_googlemail.com
 */
public abstract class BasicManaAbility extends ActivatedManaAbilityImpl {

    public BasicManaAbility(ManaEffect effect) {
        super(Zone.BATTLEFIELD, effect, new TapSourceCost());
    }

    protected BasicManaAbility(final BasicManaAbility ability) {
        super(ability);
    }
}
