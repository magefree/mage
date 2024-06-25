
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueConditionHint;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.watchers.common.PermanentsEnteredBattlefieldWatcher;

import java.util.List;

/**
 * @author Susucr
 */
public enum CelebrationCondition implements Condition {
    instance;
    private static final Hint hint = new ValueConditionHint(CelebrationNonlandsThatEnteredThisTurnCount.instance, instance);

    @Override
    public boolean apply(Game game, Ability source) {
        return CelebrationNonlandsThatEnteredThisTurnCount.instance.calculate(game, source, null) >= 2;
    }

    @Override
    public String toString() {
        return "two or more nonland permanents entered the battlefield under your control this turn";
    }

    public static Hint getHint() {
        return hint;
    }
}

enum CelebrationNonlandsThatEnteredThisTurnCount implements DynamicValue {
    instance;


    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        PermanentsEnteredBattlefieldWatcher watcher = game.getState().getWatcher(PermanentsEnteredBattlefieldWatcher.class);
        if (watcher != null) {
            List<Permanent> list = watcher.getThisTurnEnteringPermanents(sourceAbility.getControllerId());
            return (int) list.stream().filter(x -> !x.isLand(game)).count();
        }
        return 0;
    }

    @Override
    public DynamicValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "nonland permanents that entered under your control this turn";
    }

}
