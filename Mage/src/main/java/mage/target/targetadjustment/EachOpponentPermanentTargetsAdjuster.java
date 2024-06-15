package mage.target.targetadjustment;

import mage.abilities.Ability;
import mage.filter.Filter;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author notgreat
 */
public class EachOpponentPermanentTargetsAdjuster implements TargetAdjuster {
    private TargetPermanent blueprintTarget = null;
    /**
     * Duplicates the permanent target for each opponent.
     * Filtering of permanent's controllers will be handled inside, so
     * do not pass a blueprint target with a controller restriction filter/predicate.
     */
    public EachOpponentPermanentTargetsAdjuster() {
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (blueprintTarget == null) {
            blueprintTarget = (TargetPermanent) ability.getTargets().get(0).copy();
        }
        ability.getTargets().clear();
        for (UUID opponentId : game.getOpponents(ability.getControllerId())) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent == null) {
                continue;
            }
            TargetPermanent newTarget = blueprintTarget.copy();
            Filter<Permanent> filter = newTarget.getFilter();
            filter.add(new ControllerIdPredicate(opponentId));
            if (!newTarget.possibleTargets(ability.getControllerId(), ability, game).isEmpty()) {
                newTarget.withTargetName(filter.getMessage() + " controlled by " + opponent.getLogName());
                ability.addTarget(newTarget);
            }
        }
    }
}
