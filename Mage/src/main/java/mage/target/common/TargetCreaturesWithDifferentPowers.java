package mage.target.common;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public class TargetCreaturesWithDifferentPowers extends TargetPermanent {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("creatures with different powers");

    public TargetCreaturesWithDifferentPowers() {
        super(0, Integer.MAX_VALUE, filter, false);
    }

    private TargetCreaturesWithDifferentPowers(final TargetCreaturesWithDifferentPowers target) {
        super(target);
    }

    @Override
    public TargetCreaturesWithDifferentPowers copy() {
        return new TargetCreaturesWithDifferentPowers(this);
    }

    @Override
    public boolean canTarget(UUID controllerId, UUID id, Ability source, Game game) {
        if (!super.canTarget(controllerId, id, source, game)) {
            return false;
        }
        Permanent creature = game.getPermanent(id);
        if (creature == null) {
            return false;
        }
        return this.getTargets()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .map(MageObject::getPower)
                .mapToInt(MageInt::getValue)
                .noneMatch(p -> creature.getPower().getValue() == p);
    }
}
