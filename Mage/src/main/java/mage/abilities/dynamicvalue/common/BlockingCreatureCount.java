package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.game.combat.CombatGroup;

import java.util.UUID;

/**
 * @author awjackson
 */
public enum BlockingCreatureCount implements DynamicValue {
    SOURCE("creature blocking it"),
    TARGET("creature blocking it"),
    BEYOND_FIRST("creature blocking it beyond the first");

    private final String message;

    BlockingCreatureCount(String message) {
        this.message = message;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        UUID attackerId = (this == TARGET ? effect.getTargetPointer().getFirst(game, sourceAbility) : sourceAbility.getSourceId());
        for (CombatGroup combatGroup : game.getCombat().getGroups()) {
            if (!combatGroup.getAttackers().contains(attackerId)) {
                continue;
            }
            int blockers = combatGroup.getBlockers().size();
            if (this == BEYOND_FIRST) {
                blockers = Math.max(blockers - 1, 0);
            }
            return blockers;
        }
        return 0;
    }

    @Override
    public BlockingCreatureCount copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "1";
    }
}
