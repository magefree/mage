package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.watchers.common.SpellsCastWatcher;

import java.util.List;
import java.util.Objects;

/**
 * @author balazskristof, Jmlundeen
 */
public enum CastAnotherSpellThisTurnCondition implements Condition {
    instance;
    private final Hint hint = new ConditionHint(
            this, "You've cast another spell this turn"
    );

    @Override
    public boolean apply(Game game, Ability source) {
        SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
        if (watcher == null) {
            return false;
        }
        // a trigger checks its intervening if before it goes on the stack, so its own ZCC is not set yet
        int sourceZcc = source.getStackMomentSourceZCC() != 0
                ? source.getStackMomentSourceZCC()
                : game.getState().getZoneChangeCounter(source.getSourceId());
        List<Spell> spells = watcher.getSpellsCastThisTurn(source.getControllerId());
        return spells != null && spells
                .stream()
                .filter(Objects::nonNull)
                .anyMatch(spell -> !spell.getSourceId().equals(source.getSourceId()) || spell.getZoneChangeCounter(game) != sourceZcc);
    }

    public Hint getHint() {
        return hint;
    }

    @Override
    public String toString() {
        return "you've cast another spell this turn";
    }
}
