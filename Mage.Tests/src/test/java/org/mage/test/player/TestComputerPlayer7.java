package org.mage.test.player;

import mage.abilities.Ability;
import mage.cards.Cards;
import mage.choices.Choice;
import mage.constants.Outcome;
import mage.constants.RangeOfInfluence;
import mage.game.Game;
import mage.player.ai.ComputerPlayer7;
import mage.target.Target;
import mage.target.TargetCard;

/**
 * AI: helper class for tests
 * <p>
 * Copied-pasted methods from TestComputerPlayer, see docs in there
 *
 * @author JayDi85
 */

public final class TestComputerPlayer7 extends ComputerPlayer7 {

    private TestPlayer testPlayerLink;

    public TestComputerPlayer7(String name, RangeOfInfluence range, int skill) {
        super(name, range, skill);
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
    public boolean choose(Outcome outcome, Cards cards, TargetCard target, Ability source, Game game) {
        if (testPlayerLink.canChooseByComputer()) {
            return super.choose(outcome, cards, target, source, game);
        } else {
            return testPlayerLink.choose(outcome, cards, target, source, game);
        }
    }

    @Override
    public boolean chooseTarget(Outcome outcome, Target target, Ability source, Game game) {
        if (testPlayerLink.canChooseByComputer()) {
            return super.chooseTarget(outcome, target, source, game);
        } else {
            return testPlayerLink.chooseTarget(outcome, target, source, game);
        }
    }

    @Override
    public boolean chooseTarget(Outcome outcome, Cards cards, TargetCard target, Ability source, Game game) {
        if (testPlayerLink.canChooseByComputer()) {
            return super.chooseTarget(outcome, cards, target, source, game);
        } else {
            return testPlayerLink.chooseTarget(outcome, cards, target, source, game);
        }
    }

    @Override
    public boolean flipCoinResult(Game game) {
        // same random results must be same in any mode
        return testPlayerLink.flipCoinResult(game);
    }

    @Override
    public int rollDieResult(int sides, Game game) {
        // same random results must be same in any mode
        return testPlayerLink.rollDieResult(sides, game);
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
