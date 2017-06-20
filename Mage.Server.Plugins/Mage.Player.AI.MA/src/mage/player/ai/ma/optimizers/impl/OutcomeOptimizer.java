/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mage.player.ai.ma.optimizers.impl;

import java.util.List;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.constants.Outcome;
import mage.game.Game;

/**
 * Removes abilities that require only discard a card for activation.
 *
  * @author LevelX2
 */
public class OutcomeOptimizer extends BaseTreeOptimizer {

    @Override
    public void filter(Game game, List<Ability> actions) {
        for (Ability ability : actions) {
            for (Effect effect: ability.getEffects()) {
                if (effect.getOutcome() == Outcome.AIDontUseIt) {
                    removeAbility(ability);
                    break;
                }
            }
        }
    }
}
