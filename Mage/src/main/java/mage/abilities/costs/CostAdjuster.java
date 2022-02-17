package mage.abilities.costs;

import mage.abilities.Ability;
import mage.game.Game;

import java.io.Serializable;

/**
 * @author TheElk801
 */
@FunctionalInterface
public interface CostAdjuster extends Serializable {

    /**
     * Must check playable and real cast states.
     * Example: if it need stack related info (like real targets) then must check two states (game.inCheckPlayableState):
     * 1. In playable state it must check all possible use cases (e.g. allow to reduce on any available target and modes)
     * 2. In real cast state it must check current use case (e.g. real selected targets and modes)
     *
     * @param ability
     * @param game
     */
    void adjustCosts(Ability ability, Game game);
}
