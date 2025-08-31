package mage.target.common;

import mage.abilities.Ability;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class TargetTappedPermanentAsYouCast extends TargetPermanent {

    public TargetTappedPermanentAsYouCast() {}

    public TargetTappedPermanentAsYouCast(FilterPermanent filter) {
        this.filter = filter;
        this.targetName = filter.getMessage();
    }

    private TargetTappedPermanentAsYouCast(TargetTappedPermanentAsYouCast target) {
        super(target);
    }

    @Override
    public TargetTappedPermanentAsYouCast copy() {
        return new TargetTappedPermanentAsYouCast(this);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> possibleTargets = game.getBattlefield().getActivePermanents(getFilter(), sourceControllerId, source, game).stream()
                .filter(Permanent::isTapped)
                .map(Permanent::getId)
                .collect(Collectors.toSet());
        return keepValidPossibleTargets(possibleTargets, sourceControllerId, source, game);
    }

    @Override
    public boolean canChoose(UUID sourceControllerId, Ability source, Game game) {
        return canChooseFromPossibleTargets(sourceControllerId, source, game);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        if (super.canTarget(playerId, id, source, game)) {
            Permanent permanent = game.getPermanent(id);
            return permanent != null && permanent.isTapped();
        }
        return false;
    }

    // See ruling: https://www.mtgsalvation.com/forums/magic-fundamentals/magic-rulings/magic-rulings-archives/253345-dream-leash
    @Override
    public boolean stillLegalTarget(UUID controllerId, UUID id, Ability source, Game game) {
        // on resolve must ignore tapped status

        // The middle ability of Enthralling Hold affects only the choice of target as the spell is cast.
        // If the creature becomes untapped before the spell resolves, it still resolves. If a player is allowed
        // to change the spell's target while it's on the stack, they may choose an untapped creature. If you put
        // Enthralling Hold onto the battlefield without casting it, you may attach it to an untapped creature.
        // (2020-06-23)

        Permanent permanent = game.getPermanent(id);
        return permanent != null 
                && getFilter().match(permanent, game)
                && super.canTarget(controllerId, id, source, game);  // check everything but leave out the tapped requirement
    }
}
