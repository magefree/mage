package mage.abilities.common;

import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.condition.Condition;
import mage.abilities.costs.Cost;
import mage.abilities.effects.Effect;
import mage.constants.TimingRule;
import mage.constants.Zone;

/**
 * @author weirddan455
 */
public class ActivateOncePerGameActivatedAbility extends ActivatedAbilityImpl {

    public ActivateOncePerGameActivatedAbility(Effect effect, Cost cost) {
        this(Zone.BATTLEFIELD, effect, cost, TimingRule.INSTANT);
    }

    public ActivateOncePerGameActivatedAbility(Effect effect, Cost cost, Condition condition) {
        this(Zone.BATTLEFIELD, effect, cost, condition, TimingRule.INSTANT);
    }

    public ActivateOncePerGameActivatedAbility(Zone zone, Effect effect, Cost cost, Condition condition) {
        this(zone, effect, cost, condition, TimingRule.INSTANT);
    }

    public ActivateOncePerGameActivatedAbility(Zone zone, Effect effect, Cost cost, TimingRule timingRule) {
        this(zone, effect, cost, null, timingRule);
    }

    public ActivateOncePerGameActivatedAbility(Zone zone, Effect effect, Cost cost, Condition condition, TimingRule timingRule) {
        super(zone, effect, cost);
        this.condition = condition;
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
        if (condition != null) {
            String message = condition.toString();
            sb.append("only ").append(message.startsWith("if ") || message.startsWith("during") ? message : "if " + message).append(" and ");
        }
        if (this.timing == TimingRule.SORCERY) {
            sb.append("only as a sorcery and ");
        }
        sb.append("only once.");
        return sb.toString();
    }
}
