package mage.abilities.condition.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.constants.SuperType;
import mage.game.Game;

import java.util.Objects;

/**
 *
 * @author LevelX2
 */
public class TargetHasSuperTypeCondition implements Condition {

    private final SuperType superType;

    public TargetHasSuperTypeCondition(SuperType superType) {
        this.superType = superType;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (!source.getTargets().isEmpty()) {
            MageObject mageObject = game.getObject(source.getFirstTarget());
            if (mageObject != null) {
                return mageObject.getSuperType().contains(superType);
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
        TargetHasSuperTypeCondition that = (TargetHasSuperTypeCondition) obj;
        return this.superType == that.superType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(superType);
    }
}
