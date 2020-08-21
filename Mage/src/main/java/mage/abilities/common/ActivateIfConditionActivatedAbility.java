
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
        this.condition = condition;
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
        if (condition instanceof InvertCondition) {
            sb.append(" You can't activate this ability ");
        } else {
            sb.append(" Activate this ability only ");
        }
        if (!condition.toString().startsWith("during")
                && !condition.toString().startsWith("before")
                && !condition.toString().startsWith("if")) {
            sb.append("if ");
        }
        sb.append(condition.toString()).append('.');

        return sb.toString();
    }

    @Override
    public ActivateIfConditionActivatedAbility copy() {
        return new ActivateIfConditionActivatedAbility(this);
    }

}
