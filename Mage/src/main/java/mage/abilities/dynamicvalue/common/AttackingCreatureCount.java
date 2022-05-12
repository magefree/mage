package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public class AttackingCreatureCount implements DynamicValue {

    private String message;
    private FilterCreaturePermanent filter;

    public AttackingCreatureCount() {
        this("attacking creature");
    }

    public AttackingCreatureCount(FilterCreaturePermanent filter) {
        this(filter, "attacking " + filter.getMessage());
    }

    public AttackingCreatureCount(String message) {
        this(null, message);
    }

    public AttackingCreatureCount(FilterCreaturePermanent filter, String message) {
        this.message = message;
        this.filter = filter;
    }

    public AttackingCreatureCount(final AttackingCreatureCount dynamicValue) {
        super();
        this.message = dynamicValue.message;
        this.filter = dynamicValue.filter;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int count = 0;
        for (CombatGroup combatGroup : game.getCombat().getGroups()) {
            for (UUID permId : combatGroup.getAttackers()) {
                if (filter != null) {
                    Permanent attacker = game.getPermanent(permId);
                    if (filter.match(attacker, sourceAbility.getControllerId(), sourceAbility, game)) {
                        count++;
                    }
                } else {
                    count++;
                }
            }
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
