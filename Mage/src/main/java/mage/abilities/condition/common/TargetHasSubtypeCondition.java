
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author LevelX2
 */

public class TargetHasSubtypeCondition implements Condition {

    private final List<SubType> subtypes = new ArrayList<>();

    public TargetHasSubtypeCondition(SubType... subTypes) {
        this.subtypes.addAll(Arrays.asList(subTypes));
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (!source.getTargets().isEmpty()) {
            Permanent permanent = game.getPermanent(source.getFirstTarget());
            if (permanent == null) {
                return false;
            }
            for (SubType subtype : subtypes) {
                if (permanent.hasSubtype(subtype, game)) {
                    return true;
                }
            }
        }
        return false;
    }
}
