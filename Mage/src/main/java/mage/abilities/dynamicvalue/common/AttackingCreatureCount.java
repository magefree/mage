
package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.game.combat.CombatGroup;

/**
 *
 * @author LevelX2
 */
public class AttackingCreatureCount implements DynamicValue {

    private String message;

    public AttackingCreatureCount() {
        this("attacking creature");
    }

    public AttackingCreatureCount(String message) {
        this.message = message;
    }

    public AttackingCreatureCount(final AttackingCreatureCount dynamicValue) {
        super();
        this.message = dynamicValue.message;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int count = 0;
        for (CombatGroup combatGroup : game.getCombat().getGroups()) {
            count += combatGroup.getAttackers().size();
        }
        return count;
    }

    @Override
    public AttackingCreatureCount copy() {
        return new AttackingCreatureCount(this);
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "X";
    }
}
