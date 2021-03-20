package mage.player.ai.ma.optimizers.impl;

import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.constants.Outcome;
import mage.game.Game;

import java.util.List;

/**
 * Removes abilities that require only discard a card for activation.
 *
 * @author LevelX2
 */
public class OutcomeOptimizer extends BaseTreeOptimizer {

    @Override
    public void filter(Game game, List<Ability> actions) {
        for (Ability ability : actions) {
            for (Effect effect : ability.getEffects()) {
                if (ability.getCustomOutcome() == Outcome.AIDontUseIt || effect.getOutcome() == Outcome.AIDontUseIt) {
                    removeAbility(ability);
                    break;
                }
            }
        }
    }
}
