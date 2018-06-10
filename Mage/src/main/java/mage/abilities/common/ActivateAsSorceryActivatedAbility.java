
package mage.abilities.common;

import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.effects.Effect;
import mage.constants.TimingRule;
import mage.constants.Zone;

public class ActivateAsSorceryActivatedAbility extends ActivatedAbilityImpl {
    public ActivateAsSorceryActivatedAbility(Zone zone, Effect effect, Cost cost) {
        super(zone, effect, cost);
        timing = TimingRule.SORCERY;
    }

    public ActivateAsSorceryActivatedAbility(final ActivateAsSorceryActivatedAbility ability) {
        super(ability);
    }

    @Override
    public ActivateAsSorceryActivatedAbility copy() {
        return new ActivateAsSorceryActivatedAbility(this);
    }

    @Override
    public String getRule() {
        return super.getRule() + " Activate this ability only any time you could cast a sorcery.";
    }
}
