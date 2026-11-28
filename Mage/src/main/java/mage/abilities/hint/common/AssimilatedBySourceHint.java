package mage.abilities.hint.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.common.AssimilateTargetEffect;
import mage.abilities.hint.Hint;
import mage.game.Game;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author JayDi85
 */
public enum AssimilatedBySourceHint implements Hint {
    instance;

    @Override
    public String getText(Game game, Ability ability) {
        if (ability.getSourcePermanentIfItStillExists(game) == null) {
            return null;
        }
        List<String> names = AssimilateTargetEffect
                .getAssimilatedPermanents(game, ability)
                .stream()
                .map(MageObject::getLogName)
                .sorted()
                .collect(Collectors.toList());
        return "Assimilated permanents: " + names.size()
                + (names.isEmpty() ? "" : " (" + String.join(", ", names) + ')');
    }

    @Override
    public AssimilatedBySourceHint copy() {
        return this;
    }
}
