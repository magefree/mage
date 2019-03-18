
package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.game.combat.CombatGroup;

/**
 *
 * @author Markedagain
 */
public class BlockedCreatureCount implements DynamicValue {

    private String message;
    boolean beyondTheFirst;

    public BlockedCreatureCount() {
        this("each creature blocking it");
    }

    public BlockedCreatureCount(String message) {
        this(message, false);
    }

    public BlockedCreatureCount(String message, boolean beyondTheFirst) {
        this.message = message;
        //this.beyondTheFirst = beyondTheFirst; this was never set in the original, so not setting here just in case ??
    }

    public BlockedCreatureCount(final BlockedCreatureCount dynamicValue) {
        super();
        this.message = dynamicValue.message;
        this.beyondTheFirst = dynamicValue.beyondTheFirst;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        for (CombatGroup combatGroup : game.getCombat().getGroups()) {
            if (combatGroup.getAttackers().contains(sourceAbility.getSourceId())) {
                int blockers = combatGroup.getBlockers().size();
                if (beyondTheFirst) {
                    blockers = blockers > 0 ? blockers - 1 : 0;
                }
                return blockers;
            }
        }
        return 0;
    }

    @Override
    public BlockedCreatureCount copy() {
        return new BlockedCreatureCount(this);
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
