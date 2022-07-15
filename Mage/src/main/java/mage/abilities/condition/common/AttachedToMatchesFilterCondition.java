
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Objects;

/**
 * Describes condition when equipped permanent has superType
 *
 * @author LevelX
 */
public class AttachedToMatchesFilterCondition implements Condition {

    private final FilterPermanent filter;

    public AttachedToMatchesFilterCondition(FilterPermanent filter) {
        this.filter = filter;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getBattlefield().getPermanent(source.getSourceId());
        if (permanent != null && permanent.getAttachedTo() != null) {
            Permanent attachedTo = game.getBattlefield().getPermanent(permanent.getAttachedTo());
            if (attachedTo == null) {
                attachedTo = (Permanent) game.getLastKnownInformation(permanent.getAttachedTo(), Zone.BATTLEFIELD);
            }
            if (filter.match(attachedTo, attachedTo.getControllerId(), source, game)) {
                return true;
            }

        }
        return false;
    }

    @Override
    public String toString() {
        return filter.getMessage();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        AttachedToMatchesFilterCondition other = (AttachedToMatchesFilterCondition) obj;

        return Objects.equals(this.filter, other.filter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filter);
    }
}
