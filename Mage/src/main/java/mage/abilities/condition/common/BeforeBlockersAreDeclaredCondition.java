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

    private static final BeforeBlockersAreDeclaredCondition fInstance = new BeforeBlockersAreDeclaredCondition();

    public static Condition getInstance() {
        return fInstance;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return !(game.getStep().getType().equals(PhaseStep.DECLARE_BLOCKERS)
                || game.getStep().getType().equals(PhaseStep.FIRST_COMBAT_DAMAGE)
                || game.getStep().getType().equals(PhaseStep.COMBAT_DAMAGE)
                || game.getStep().getType().equals(PhaseStep.END_COMBAT));
    }

    @Override
    public String toString() {
        return "before blockers are declared";
    }
}
