package mage.player.ai.ma.optimizers.impl;

import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.constants.Outcome;
import mage.game.Game;

import java.util.List;

/**
 * AI: removes abilities that AI don't know how to use
 *
 * @author LevelX2
 */
public class OutcomeOptimizer extends BaseTreeOptimizer {

    @Override
    public void filter(Game game, List<Ability> actions, List<Ability> actionsToRemove) {
        for (Ability ability : actions) {
            for (Effect effect : ability.getEffects()) {
                if (ability.getCustomOutcome() == Outcome.AIDontUseIt || effect.getOutcome() == Outcome.AIDontUseIt) {
                    actionsToRemove.add(ability);
                    break;
                }
            }
        }
    }
}
