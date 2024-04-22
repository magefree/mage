package mage.abilities.common;

import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.condition.Condition;
import mage.abilities.condition.InvertCondition;
import mage.abilities.costs.Cost;
import mage.abilities.effects.Effect;
import mage.constants.TimingRule;
import mage.constants.Zone;

/**
 * @author LevelX2
 */
public class ActivateIfConditionActivatedAbility extends ActivatedAbilityImpl {

    public ActivateIfConditionActivatedAbility(Zone zone, Effect effect, Cost cost, Condition condition) {
        this(zone, effect, cost, condition, TimingRule.INSTANT);
    }

    public ActivateIfConditionActivatedAbility(Zone zone, Effect effect, Cost cost, Condition condition, TimingRule timing) {
        super(zone, effect, cost);
        this.condition = condition;
        this.timing = timing;
    }

    protected ActivateIfConditionActivatedAbility(final ActivateIfConditionActivatedAbility ability) {
        super(ability);
    }

    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder(super.getRule());
        if (condition instanceof InvertCondition) {
            sb.append(" You can't activate this ability ");
        } else {
            sb.append(" Activate only ");
        }
        if (!condition.toString().startsWith("during")
                && !condition.toString().startsWith("before")
                && !condition.toString().startsWith("if")) {
            sb.append("if ");
        }
        sb.append(condition.toString());
        if (timing == TimingRule.SORCERY) {
            sb.append(" and only as a sorcery");
        }
        sb.append('.');

        return sb.toString();
    }

    @Override
    public ActivateIfConditionActivatedAbility copy() {
        return new ActivateIfConditionActivatedAbility(this);
    }

}
