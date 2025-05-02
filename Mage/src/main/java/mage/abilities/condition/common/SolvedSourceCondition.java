package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * Checks if a Permanent is solved
 *
 * @author DominionSpy
 */
public enum SolvedSourceCondition implements Condition {
    SOLVED(true),
    UNSOLVED(false);
    private final boolean solved;

    SolvedSourceCondition(boolean solved) {
        this.solved = solved;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        return permanent != null && permanent.isSolved() == solved;
    }

    @Override
    public String toString() {
        return "{this} is " + (solved ? "solved" : "unsolved");
    }
}
