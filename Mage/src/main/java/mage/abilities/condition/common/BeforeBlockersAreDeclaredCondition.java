/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.constants.PhaseStep;
import mage.game.Game;

/**
 * This condtion does not check that the turnPhase is combat. You have to check
 * this if needed on another place.
 *
 * @author LevelX2
 */
public class BeforeBlockersAreDeclaredCondition implements Condition {

    private static final BeforeBlockersAreDeclaredCondition instance = new BeforeBlockersAreDeclaredCondition();

    public static Condition getInstance() {
        return instance;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return game.getStep().getType().isBefore(PhaseStep.DECLARE_BLOCKERS);
    }

    @Override
    public String toString() {
        return "before blockers are declared";
    }
}
