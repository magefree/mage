package mage.abilities.hint.common;

import mage.abilities.Ability;
import mage.abilities.condition.common.HaveInitiativeCondition;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.game.Game;

import java.util.Optional;

/**
 * @author JayDi85
 */
public enum InitiativeHint implements Hint {

    instance;
    private static final ConditionHint hint = new ConditionHint(HaveInitiativeCondition.instance, "You have the Initiative");

    @Override
    public String getText(Game game, Ability ability) {
        String res = hint.getText(game, ability);
        if (game.getInitiativeId() == null) {
            // no initiative
            return res + " (no Initiative in the game)";
        } else {
            // player
            return res + Optional.ofNullable(game.getPlayer(game.getInitiativeId()))
                    .map(p -> " (current owner: " + p.getName() + ")")
                    .orElse("");
        }
    }

    @Override
    public Hint copy() {
        return instance;
    }
}
