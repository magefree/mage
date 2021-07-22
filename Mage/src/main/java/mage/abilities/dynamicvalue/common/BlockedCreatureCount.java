
package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.game.combat.CombatGroup;

/**
 * @author Markedagain
 */
public enum BlockedCreatureCount implements DynamicValue {
    ALL("each creature blocking it", false),
    BEYOND_FIRST("each creature blocking it beyond the first", true);

    private final String message;
    private final boolean beyondTheFirst;

    BlockedCreatureCount(String message, boolean beyondTheFirst) {
        this.message = message;
        this.beyondTheFirst = beyondTheFirst;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        for (CombatGroup combatGroup : game.getCombat().getGroups()) {
            if (!combatGroup.getAttackers().contains(sourceAbility.getSourceId())) {
                continue;
            }
            int blockers = combatGroup.getBlockers().size();
            if (beyondTheFirst) {
                blockers = blockers > 0 ? blockers - 1 : 0;
            }
            return blockers;
        }
        return 0;
    }

    @Override
    public BlockedCreatureCount copy() {
        return this;
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
