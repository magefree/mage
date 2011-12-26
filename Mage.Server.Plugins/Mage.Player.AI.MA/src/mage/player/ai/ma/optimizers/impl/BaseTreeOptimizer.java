package mage.player.ai.ma.optimizers.impl;

import mage.abilities.Ability;
import mage.game.Game;
import mage.player.ai.ma.optimizers.TreeOptimizer;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for tree optimizers.
 *
 * @author ayratn
 */
public abstract class BaseTreeOptimizer implements TreeOptimizer {

    /**
     * List of abilities that should be removed because of optimization.
     *
     */
    protected List<Ability> toRemove;

    /**
     * Inner method for filtering actions.
     * Should be implemented by classes.
     *
     * @param game
     * @param actions
     */
    abstract void filter(Game game, List<Ability> actions);

    /**
     * Template method for optimization.
     *
     * @param game
     * @param actions
     */
    @Override
    public final void optimize(Game game, List<Ability> actions) {
        filter(game, actions);

        if (toRemove != null) {
            for (Ability r : toRemove) {
                actions.remove(r);
            }
        }
    }

    /**
     * Mark an ability to be removed
     * Not thread-safe for performance reasons.
     *
     * @param ability
     */
    protected void removeAbility(Ability ability) {
        if (toRemove == null) {
            toRemove = new ArrayList<Ability>();
        }
        toRemove.add(ability);
    }
}
