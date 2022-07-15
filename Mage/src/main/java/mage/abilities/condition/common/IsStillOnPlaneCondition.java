
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.game.command.Plane;

import java.util.Objects;

/**
 * @author spjspj
 */
public class IsStillOnPlaneCondition implements Condition {

    private String planeName;

    public IsStillOnPlaneCondition(String planeName) {
        this.planeName = planeName;
    }
    
    public IsStillOnPlaneCondition(IsStillOnPlaneCondition condition) {        
        this.planeName = condition.planeName;
    }

    @Override
    public boolean apply(Game game, Ability source) {

        Plane plane = game.getState().getCurrentPlane();
        if (plane != null) {
            if (plane.getName().equalsIgnoreCase(planeName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(planeName);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final IsStillOnPlaneCondition other = (IsStillOnPlaneCondition) obj;
        return Objects.equals(this.planeName, other.planeName);
    }
}
