package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;

/**
 * @author emerald000
 */
public enum RevealTargetFromHandCostCount implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        for (Cost cost : sourceAbility.getCosts()) {
            if (cost instanceof RevealTargetFromHandCost) {
                return ((RevealTargetFromHandCost) cost).getNumberRevealedCards();
            }
        }
        return 0;
    }

    @Override
    public String getMessage() {
        return "";
    }

    @Override
    public RevealTargetFromHandCostCount copy() {
        return instance;
    }

    @Override
    public String toString() {
        return "X";
    }

}
