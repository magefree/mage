package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.game.Game;
import mage.watchers.common.PermanentsSacrificedWatcher;

/**
 * @author TheElk801
 */
public enum SacrificedArtifactThisTurnCondition implements Condition {
    instance;
    private static final Hint hint = new ConditionHint(instance, "You sacrificed an artifact this turn");

    public static Hint getHint() {
        return hint;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return game
                .getState()
                .getWatcher(PermanentsSacrificedWatcher.class)
                .getThisTurnSacrificedPermanents(source.getControllerId())
                .stream()
                .anyMatch(permanent -> permanent.isArtifact(game));
    }
}
