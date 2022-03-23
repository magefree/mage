package org.mage.test.player;

import mage.abilities.Ability;
import mage.choices.Choice;
import mage.constants.Outcome;
import mage.constants.RangeOfInfluence;
import mage.game.Game;
import mage.player.ai.ComputerPlayer;
import mage.target.Target;

/**
 * @author JayDi85
 */

/**
 * Mock class to inject test player support in the inner choice calls, e.g. in PlayerImpl. If you
 * want to set up inner choices then override it here.
 * <p>
 * Works in strict mode only.
 * <p>
 * If you catch overflow error with new method then check strict mode in it.
 * <p>
 * Example 1: TestPlayer's code uses outer computerPlayer call to discard but discard's inner code must call choose from TestPlayer
 * Example 2: TestPlayer's code uses outer computerPlayer call to flipCoin but flipCoin's inner code must call flipCoinResult from TestPlayer
 * <p>
 * Don't forget to add new methods in another classes like TestComputerPlayer7 or TestComputerPlayerMonteCarlo
 * <p>
 * If you implement set up of random results for tests (die roll, flip coin, etc) and want to support AI tests
 * (same random results in simulated games) then override same methods in SimulatedPlayer2 too
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
    public boolean choose(Outcome outcome, Target target, Ability source, Game game) {
        if (testPlayerLink.canChooseByComputer()) {
            return super.choose(outcome, target, source, game);
        } else {
            return testPlayerLink.choose(outcome, target, source, game);
        }
    }

    @Override
    public boolean choose(Outcome outcome, Choice choice, Game game) {
        if (testPlayerLink.canChooseByComputer()) {
            return super.choose(outcome, choice, game);
        } else {
            return testPlayerLink.choose(outcome, choice, game);
        }
    }

    @Override
    public boolean flipCoinResult(Game game) {
        if (testPlayerLink.canChooseByComputer()) {
            return super.flipCoinResult(game);
        } else {
            return testPlayerLink.flipCoinResult(game);
        }
    }

    @Override
    public int rollDieResult(int sides, Game game) {
        if (testPlayerLink.canChooseByComputer()) {
            return super.rollDieResult(sides, game);
        } else {
            return testPlayerLink.rollDieResult(sides, game);
        }
    }

    @Override
    public boolean isComputer() {
        if (testPlayerLink.canChooseByComputer()) {
            return super.isComputer();
        } else {
            return testPlayerLink.isComputer();
        }
    }
}


