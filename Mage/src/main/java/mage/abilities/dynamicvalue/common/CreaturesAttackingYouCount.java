package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.game.Game;
import mage.game.combat.CombatGroup;

/**
 * @author JayDi85
 */
public enum CreaturesAttackingYouCount implements DynamicValue {

    instance;

    private static final Hint hint = new ValueHint("Creatures attacking you", instance);

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int count = 0;
        for (CombatGroup combatGroup : game.getCombat().getGroups()) {
            if (combatGroup.getDefenderId() != null && combatGroup.getDefenderId().equals(sourceAbility.getControllerId())) {
                count += combatGroup.getAttackers().size();
            }
        }
        return count;
    }

    @Override
    public DynamicValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "creatures attacking you";
    }

    public static Hint getHint() {
        return hint;
    }
}
