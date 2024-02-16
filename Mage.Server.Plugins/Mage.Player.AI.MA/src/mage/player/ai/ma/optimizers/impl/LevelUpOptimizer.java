package mage.player.ai.ma.optimizers.impl;

import mage.abilities.Ability;
import mage.abilities.keyword.LevelUpAbility;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;

import java.util.List;

/**
 * AI: make sure that AI won't level up whenever there are maximum possible level up counters.
 *
 * @author ayratn
 */
public class LevelUpOptimizer extends BaseTreeOptimizer {

    /**
     * Check that ability is level up ability, then compare the current counters of Level type to maximum.
     *
     * @param game
     * @param actions
     */
    @Override
    public void filter(Game game, List<Ability> actions, List<Ability> actionsToRemove) {
        for (Ability ability : actions) {
            if (ability instanceof LevelUpAbility) {
                Permanent permanent = game.getPermanent(ability.getSourceId());
                if (permanent instanceof PermanentCard) {
                    PermanentCard leveler = (PermanentCard) permanent;
                    // check already existing Level counters and compare to maximum that make sense
                    if (permanent.getCounters(game).getCount(CounterType.LEVEL) >= leveler.getMaxLevelCounters()) {
                        actionsToRemove.add(ability);
                    }
                }
            }
        }
    }
}
