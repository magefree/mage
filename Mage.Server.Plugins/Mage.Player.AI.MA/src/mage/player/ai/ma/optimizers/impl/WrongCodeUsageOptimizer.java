package mage.player.ai.ma.optimizers.impl;

import mage.abilities.Ability;
import mage.abilities.common.PassAbility;
import mage.game.Game;

import java.util.List;

/**
 * AI: runtime checks for possible errors or use case, must be added first in optimizers list
 *
 * @author JayDi85
 */
public class WrongCodeUsageOptimizer extends BaseTreeOptimizer {

    @Override
    public void filter(Game game, List<Ability> actions, List<Ability> actionsToRemove) {
        // runtime check: pass ability must be all the time
        if (actions.stream().filter(a -> a instanceof PassAbility).count() != 1) {
            throw new IllegalArgumentException("Wrong code usage. AI's actions list must contains only 1 instance of PassAbility");
        }
    }
}
