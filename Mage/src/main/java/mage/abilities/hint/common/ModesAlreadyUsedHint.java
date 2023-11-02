package mage.abilities.hint.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.hint.Hint;
import mage.game.Game;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Hint at all the used modes for modal effects that use
 * "Choose one that hasn't been chosen" or "Choose one that hasn't been chosen this turn".
 * <p>
 * Note: the modes need to be set up with a modeTag in order for the hint to find them.
 *
 * @author Susucr
 */
public enum ModesAlreadyUsedHint implements Hint {
    instance;

    @Override
    public String getText(Game game, Ability ability) {
        List<String> used = ability
                .getModes()
                .streamAlreadySelectedModes(ability, game)
                .map(Mode::getModeTag)
                .filter(tag -> tag != null && !tag.isEmpty())
                .collect(Collectors.toList());

        if (used.isEmpty()) {
            return "";
        }

        return "Already used"
                + (ability.getModes().isLimitUsageResetOnNewTurn() ? " this turn" : "")
                + ": [" + String.join(", ", used) + "]";
    }

    @Override
    public ModesAlreadyUsedHint copy() {
        return instance;
    }
}