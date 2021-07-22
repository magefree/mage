package org.mage.test.serverside.base;

import mage.constants.RangeOfInfluence;
import org.mage.test.player.TestComputerPlayerMonteCarlo;
import org.mage.test.player.TestPlayer;

/**
 * See more details in CardTestPlayerBaseWithAIHelps
 *
 * @author JayDi85
 */
public abstract class CardTestPlayerBaseWithMonteCarloAIHelps extends CardTestPlayerBase {

    @Override
    protected TestPlayer createPlayer(String name, RangeOfInfluence rangeOfInfluence) {
        TestPlayer testPlayer = new TestPlayer(new TestComputerPlayerMonteCarlo(name, RangeOfInfluence.ONE, 6));
        testPlayer.setAIPlayer(false); // AI can't play it by itself, use AI commands
        return testPlayer;
    }
}
