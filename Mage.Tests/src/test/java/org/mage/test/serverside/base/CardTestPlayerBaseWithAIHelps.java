package org.mage.test.serverside.base;

import mage.constants.RangeOfInfluence;
import org.mage.test.player.TestComputerPlayer7;
import org.mage.test.player.TestPlayer;

/**
 * Base class but with latest computer player to test single AI commands (it's different from full AI simulation from CardTestPlayerBaseAI):
 * 1. AI don't play normal priorities (you must use ai*** commands to play it);
 * 2. AI will choose in non strict mode (it's simulated ComputerPlayer7, not simple ComputerPlayer from basic tests)
 *
 * @author JayDi85
 */
public abstract class CardTestPlayerBaseWithAIHelps extends CardTestPlayerBase {

    @Override
    protected TestPlayer createPlayer(String name, RangeOfInfluence rangeOfInfluence) {
        TestPlayer testPlayer = new TestPlayer(new TestComputerPlayer7(name, RangeOfInfluence.ONE, 6));
        testPlayer.setAIPlayer(false); // AI can't play it by itself, use AI commands
        return testPlayer;
    }
}
