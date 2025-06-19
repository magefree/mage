package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author xenohedron
 */
public enum SourceEnteredThisTurnCondition implements Condition {
    DID(true),
    DIDNT(false);
    private final boolean flag;

    SourceEnteredThisTurnCondition(boolean flag) {
        this.flag = flag;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentOrLKI(game);
        return permanent != null && (permanent.getTurnsOnBattlefield() == 0) == flag;
    }

    @Override
    public String toString() {
        return "{this} " + (flag ? "entered" : "didn't enter the battlefield") + " this turn";
    }
}
