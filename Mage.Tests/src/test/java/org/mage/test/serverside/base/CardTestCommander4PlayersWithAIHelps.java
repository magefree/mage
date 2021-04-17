package org.mage.test.serverside.base;

import mage.constants.RangeOfInfluence;
import org.mage.test.player.TestComputerPlayer7;
import org.mage.test.player.TestPlayer;

/**
 * See more details in CardTestPlayerBaseWithAIHelps
 *
 * @author JayDi85
 */
public abstract class CardTestCommander4PlayersWithAIHelps extends CardTestCommander4Players {

    @Override
    protected TestPlayer createPlayer(String name, RangeOfInfluence rangeOfInfluence) {
        // use same RangeOfInfluence.ALL as CardTestCommander4Players do
        TestPlayer testPlayer = new TestPlayer(new TestComputerPlayer7(name, RangeOfInfluence.ALL, 6));
        testPlayer.setAIPlayer(false); // AI can't play it by itself, use AI commands
        return testPlayer;
    }
}
