
package mage.abilities.dynamicvalue.common;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.permanent.Permanent;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class AttackingFilterCreatureCount implements DynamicValue {

    private FilterCreaturePermanent filter;
    private String message;
    
    public AttackingFilterCreatureCount(FilterCreaturePermanent filter) {
        this(filter, "attacking creature");
    }

    public AttackingFilterCreatureCount(FilterCreaturePermanent filter, String message) {
        this.filter = filter;
        this.message = message;
    }

    public AttackingFilterCreatureCount(final AttackingFilterCreatureCount dynamicValue) {
        super();
        this.message = dynamicValue.message;
        this.filter = dynamicValue.filter;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int count = 0;
        for (CombatGroup combatGroup : game.getCombat().getGroups()) {
            for (UUID permId : combatGroup.getAttackers()) {
                Permanent attacker = game.getPermanent(permId);
                if (filter.match(attacker, sourceAbility.getSourceId(), sourceAbility.getControllerId(), game)) {                    
                    count++;
                }
            }
        }
        return count;
    }

    @Override
    public AttackingFilterCreatureCount copy() {
        return new AttackingFilterCreatureCount(this);
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
