/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;

/**
 *
 * @author emerald000
 */
public class RevealTargetFromHandCostCount implements DynamicValue {

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
        return "number of revealed cards";
    }

    @Override
    public RevealTargetFromHandCostCount copy() {
        return new RevealTargetFromHandCostCount();
    }

    @Override
    public String toString() {
        return "X";
    }

}
