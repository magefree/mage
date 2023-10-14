package mage.player.ai.ma.optimizers.impl;

import mage.abilities.Ability;
import mage.game.Game;
import mage.player.ai.ma.optimizers.TreeOptimizer;

import java.util.ArrayList;
import java.util.List;

/**
 * AI: base class for tree optimizers.
 *
 * @author ayratn
 */
public abstract class BaseTreeOptimizer implements TreeOptimizer {

    /**
     * Filter and ignore bad actions
     *
     * @param game
     * @param actions
     * @param actionsToRemove that must be removed/ignored from a possible AI actions
     */
    abstract void filter(Game game, List<Ability> actions, List<Ability> actionsToRemove);

    @Override
    public final void optimize(Game game, List<Ability> actions) {
        List<Ability> actionsToRemove = new ArrayList<>();
        filter(game, actions, actionsToRemove);
        for (Ability r : actionsToRemove) {
            actions.remove(r);
        }
    }
}
