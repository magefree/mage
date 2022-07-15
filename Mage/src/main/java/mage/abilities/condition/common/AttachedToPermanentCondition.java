package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Objects;
import java.util.UUID;

/**
 * Source must be attached to permanent
 *
 * @author JayDi85
 */
public class AttachedToPermanentCondition implements Condition {

    final UUID permanentId;

    public AttachedToPermanentCondition(UUID permanentId) {
        this.permanentId = permanentId;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent attachment = game.getPermanent(source.getSourceId());
        Permanent permanent = game.getPermanent(this.permanentId);
        if (attachment != null && permanent != null) {
            return permanent.getAttachments().contains(attachment.getId());
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        AttachedToPermanentCondition other = (AttachedToPermanentCondition) obj;

        return Objects.equals(this.permanentId, other.permanentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(permanentId);
    }
}
