package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author xenohedron
 */
public enum SourceEnteredThisTurnCondition implements Condition {
    TRUE(true),
    FALSE(false);

    private final boolean hasEntered;

    SourceEnteredThisTurnCondition(boolean hasEntered) {
        this.hasEntered = hasEntered;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentOrLKI(game);
        return permanent != null && (permanent.getTurnsOnBattlefield() == 0) == hasEntered;
    }

    @Override
    public String toString() {
        return "{this} " + (hasEntered ? "entered" : "didn't enter the battlefield") + " this turn";
    }
}
