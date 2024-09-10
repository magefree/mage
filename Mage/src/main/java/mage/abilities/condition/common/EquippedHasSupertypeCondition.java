
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.EnumSet;
import java.util.Set;

/**
 *
 * @author LevelX2
 */

public class EquippedHasSupertypeCondition implements Condition {

    private SuperType superType;
    private Set<SuperType> superTypes = EnumSet.noneOf(SuperType.class); // scope = Any

    public EquippedHasSupertypeCondition(SuperType supertype) {
        this.superType = supertype;
    }

    public EquippedHasSupertypeCondition(Set<SuperType> superTypes) {
        this.superTypes = superTypes;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null && permanent.getAttachedTo() != null) {
            Permanent attachedTo = game.getPermanent(permanent.getAttachedTo());
            if (attachedTo == null) {
                attachedTo = (Permanent) game.getLastKnownInformation(permanent.getAttachedTo(), Zone.BATTLEFIELD);
            }
            if (attachedTo != null) {
                if (superType != null) {
                    if (attachedTo.getSuperType(game).contains(this.superType)) {
                        return true;
                    }
                } else {
                    for (SuperType s : superTypes) {
                        if (attachedTo.getSuperType(game).contains(s)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
