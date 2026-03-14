
package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.game.Game;
import mage.watchers.common.PermanentsSacrificedWatcher;

/**
 * @author Susucr
 */
public enum PermanentsSacrificedThisTurnCount implements DynamicValue {
    ALL(true),
    YOU(false);
    private final boolean all;
    private final Hint hint;

    PermanentsSacrificedThisTurnCount(boolean all) {
        this.all = all;
        this.hint = new ValueHint("Permanents " + (all ? "" : "you ") + "sacrificed this turn", this);
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        PermanentsSacrificedWatcher watcher = game.getState().getWatcher(PermanentsSacrificedWatcher.class);
        return this.all
                ? watcher.getThisTurnSacrificedPermanents()
                : watcher.getThisTurnSacrificedPermanents(sourceAbility.getControllerId()).size();
    }

    @Override
    public PermanentsSacrificedThisTurnCount copy() {
        return this;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "the number of permanents " + (this.all ? "" : "you've ") + "sacrificed this turn";
    }

    public Hint getHint() {
        return hint;
    }
}
