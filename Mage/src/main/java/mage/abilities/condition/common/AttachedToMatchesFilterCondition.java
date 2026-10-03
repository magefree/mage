
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * True if the permanent the source is attached to matches the filter.
 * The filter is matched from the source controller's point of view, so "you control" means the
 * controller of the Aura or Equipment.
 *
 * @author LevelX
 */
public class AttachedToMatchesFilterCondition implements Condition {

    private final FilterPermanent filter;

    public AttachedToMatchesFilterCondition(FilterPermanent filter) {
        this.filter = filter;
        if (filter == null) {
            throw new IllegalStateException("Wrong code usage. Filter must be non-nullable.");
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null && permanent.getAttachedTo() != null) {
            Permanent attachedTo = game.getPermanent(permanent.getAttachedTo());
            if (attachedTo == null) {
                attachedTo = (Permanent) game.getLastKnownInformation(permanent.getAttachedTo(), Zone.BATTLEFIELD);
            }
            if (attachedTo == null) {
                return false;
            }
            if (filter.match(attachedTo, source.getControllerId(), source, game)) {
                return true;
            }

        }
        return false;
    }

    @Override
    public String toString() {
        return filter.getMessage();
    }

}
