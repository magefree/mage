
package mage.abilities.common;

import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.condition.Condition;
import mage.abilities.condition.InvertCondition;
import mage.abilities.costs.Cost;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;

/**
 * @author LevelX2
 */
public class ActivateIfConditionActivatedAbility extends ActivatedAbilityImpl {

    public ActivateIfConditionActivatedAbility(Zone zone, Effect effect, Cost cost, Condition condition) {
        super(zone, effect, cost);
        setCondition(condition);
    }

    public ActivateIfConditionActivatedAbility(ActivateIfConditionActivatedAbility ability) {
        super(ability);
    }

    @Override
    public boolean resolve(Game game) {
        return super.resolve(game);
    }

    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder(super.getRule());
        if (getCondition() instanceof InvertCondition) {
            sb.append(" You can't activate this ability ");
        } else {
            sb.append(" Activate only ");
        }
        if (!getCondition().toString().startsWith("during")
                && !getCondition().toString().startsWith("before")
                && !getCondition().toString().startsWith("if")) {
            sb.append("if ");
        }
        sb.append(getCondition().toString()).append('.');

        return sb.toString();
    }

    @Override
    public ActivateIfConditionActivatedAbility copy() {
        return new ActivateIfConditionActivatedAbility(this);
    }

}
