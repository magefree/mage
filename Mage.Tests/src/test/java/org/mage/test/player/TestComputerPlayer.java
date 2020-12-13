package org.mage.test.player;

import mage.constants.Outcome;
import mage.constants.RangeOfInfluence;
import mage.game.Game;
import mage.player.ai.ComputerPlayer;
import mage.target.Target;

import java.util.UUID;

/**
 * @author JayDi85
 */

/**
 * Mock class to override AI logic for test, cause PlayerImpl uses inner calls for other methods. If you
 * want to override that methods for tests then call it here.
 * <p>
 * It's a workaround and can be bugged (if you catch overflow error with new method then TestPlayer
 * class must re-implement full method code without computerPlayer calls).
 * <p>
 * Example 1: TestPlayer's code uses outer computerPlayer call to discard but discard's inner code must call choose from TestPlayer
 * Example 2: TestPlayer's code uses outer computerPlayer call to flipCoin but flipCoin's inner code must call flipCoinResult from TestPlayer
 * <p>
 * Don't forget to add new methods in another classes like TestComputerPlayer7 or TestComputerPlayerMonteCarlo
 */

public class TestComputerPlayer extends ComputerPlayer {

    private TestPlayer testPlayerLink;

    public TestComputerPlayer(String name, RangeOfInfluence range) {
        super(name, range);
    }

    public void setTestPlayerLink(TestPlayer testPlayerLink) {
        this.testPlayerLink = testPlayerLink;
    }

    @Override
    public boolean choose(Outcome outcome, Target target, UUID sourceId, Game game) {
        return testPlayerLink.choose(outcome, target, sourceId, game);
    }

    @Override
    public boolean flipCoinResult(Game game) {
        return testPlayerLink.flipCoinResult(game);
    }
}


