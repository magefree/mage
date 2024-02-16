package mage.abilities.hint.common;

import mage.abilities.Ability;
import mage.abilities.condition.common.MonarchIsSourceControllerCondition;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.game.Game;

import java.util.Optional;

/**
 * @author JayDi85
 */
public enum MonarchHint implements Hint {

    instance;
    private static final ConditionHint hint = new ConditionHint(MonarchIsSourceControllerCondition.instance, "You are The Monarch");

    @Override
    public String getText(Game game, Ability ability) {
        String res = hint.getText(game, ability);
        if (game.getMonarchId() == null) {
            // no monarch
            return res + " (no monarch in the game)";
        } else {
            // player
            return res + Optional.ofNullable(game.getPlayer(game.getMonarchId()))
                    .map(p -> " (current monarch: " + p.getName() + ")")
                    .orElse("");
        }
    }

    @Override
    public Hint copy() {
        return instance;
    }
}
