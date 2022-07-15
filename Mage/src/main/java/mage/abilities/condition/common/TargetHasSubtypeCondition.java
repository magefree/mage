
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Objects;

/**
 *
 * @author LevelX2
 */

public class TargetHasSubtypeCondition implements Condition {

    private final SubType subtype;

    public TargetHasSubtypeCondition(SubType subtype) {
        this.subtype = subtype;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (!source.getTargets().isEmpty()) {
            Permanent permanent = game.getPermanent(source.getFirstTarget());
            if (permanent != null) {
                return permanent.hasSubtype(subtype, game);
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        TargetHasSubtypeCondition other = (TargetHasSubtypeCondition) obj;
        return this.subtype == other.subtype;
    }

    @Override
    public int hashCode() {
        return Objects.hash(subtype);
    }
}
