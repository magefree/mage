package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.game.Game;
import mage.watchers.common.PermanentsSacrificedWatcher;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

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
        return Optional.ofNullable(game
                        .getState()
                        .getWatcher(PermanentsSacrificedWatcher.class)
                        .getThisTurnSacrificedPermanents(source.getControllerId()))
                .map(List::stream)
                .orElseGet(Stream::empty)
                .anyMatch(permanent -> permanent.isArtifact(game));
    }

    @Override
    public String toString() {
        return "you've sacrificed an artifact this turn";
    }
}
