package mage.target.common;

import mage.abilities.Ability;
import mage.filter.FilterPermanent;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.Objects;
import java.util.UUID;

/**
 * @author LevelX2
 */
public class TargetPermanentSameController extends TargetPermanent {

    public TargetPermanentSameController(FilterPermanent filter) {
        this(2, filter);
    }

    public TargetPermanentSameController(int numTargets, FilterPermanent filter) {
        super(numTargets, numTargets, filter, false);
    }

    protected TargetPermanentSameController(final TargetPermanentSameController target) {
        super(target);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        if (!super.canTarget(playerId, id, source, game)) {
            return false;
        }
        if (this.getTargets().isEmpty()) {
            return true;
        }
        Permanent firstTargetPermanent = game.getPermanent(id);
        return firstTargetPermanent != null
                && this
                .getTargets()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .filter(permanent -> !permanent.equals(firstTargetPermanent))
                .map(Controllable::getControllerId)
                .allMatch(firstTargetPermanent::isControlledBy);
    }

    @Override
    public TargetPermanentSameController copy() {
        return new TargetPermanentSameController(this);
    }
}
