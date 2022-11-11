package mage.abilities.common;

import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.effects.Effect;
import mage.constants.TimingRule;
import mage.constants.Zone;

/**
 *
 * @author weirddan455
 */
public class ActivateOncePerGameActivatedAbility extends ActivatedAbilityImpl {

    public ActivateOncePerGameActivatedAbility(Zone zone, Effect effect, Cost cost, TimingRule timingRule) {
        super(zone, effect, cost);
        this.timing = timingRule;
        this.maxActivationsPerGame = 1;
    }

    private ActivateOncePerGameActivatedAbility(final ActivateOncePerGameActivatedAbility ability) {
        super(ability);
    }

    @Override
    public ActivateOncePerGameActivatedAbility copy() {
        return new ActivateOncePerGameActivatedAbility(this);
    }

    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder(super.getRule());
        sb.append(" Activate ");
        if (this.timing == TimingRule.SORCERY) {
            sb.append("only as a sorcery and ");
        }
        sb.append("only once.");
        return sb.toString();
    }
}
