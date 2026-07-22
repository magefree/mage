package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author muz
 */
public enum SourcePreparedCondition implements Condition {
    PREPARED(true),
    UNPREPARED(false);

    private final boolean prepared;

    SourcePreparedCondition(boolean prepared) {
        this.prepared = prepared;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        return permanent != null && permanent.isPrepared() == prepared;
    }

    @Override
    public String toString() {
        return "{this} is " + (prepared ? "prepared" : "unprepared");
    }
}
