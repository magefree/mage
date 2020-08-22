package org.mage.test.player;

import mage.constants.Outcome;
import mage.constants.RangeOfInfluence;
import mage.game.Game;
import mage.player.ai.ComputerPlayerMCTS;
import mage.target.Target;

import java.util.UUID;

/**
 * @author JayDi85
 */

// mock class to override AI logic in tests
public class TestComputerPlayerMonteCarlo extends ComputerPlayerMCTS {

    private TestPlayer testPlayerLink;

    public TestComputerPlayerMonteCarlo(String name, RangeOfInfluence range, int skill) {
        super(name, range, skill);
    }

    public void setTestPlayerLink(TestPlayer testPlayerLink) {
        this.testPlayerLink = testPlayerLink;
    }

    @Override
    public boolean choose(Outcome outcome, Target target, UUID sourceId, Game game) {
        // copy-paste for TestComputerXXX

        // workaround for discard spells
        // reason: TestPlayer uses outer computerPlayer to discard but inner code uses choose
        return testPlayerLink.choose(outcome, target, sourceId, game);
    }
}
