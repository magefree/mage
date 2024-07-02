package mage.target.targetadjustment;

import mage.abilities.Ability;
import mage.filter.Filter;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author notgreat
 */
public class EachOpponentPermanentTargetsAdjuster extends GenericTargetAdjuster {

    /**
     * Duplicates the permanent target for each opponent.
     * Filtering of permanent's controllers will be handled inside, so
     * do not pass a blueprint target with a controller restriction filter/predicate.
     */
    public EachOpponentPermanentTargetsAdjuster() {
    }

    @Override
    public void addDefaultTargets(Ability ability) {
        super.addDefaultTargets(ability);
        if (!(blueprintTarget instanceof TargetPermanent)) {
            throw new IllegalArgumentException("EachOpponentPermanentTargetsAdjuster must use Permanent target - " + blueprintTarget);
        }
        CardUtil.AssertNoControllerOwnerPredicates(blueprintTarget);
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        for (UUID opponentId : game.getOpponents(ability.getControllerId())) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent == null) {
                continue;
            }
            Target newTarget = blueprintTarget.copy();
            Filter filter = newTarget.getFilter();
            filter.add(new ControllerIdPredicate(opponentId));
            newTarget.withTargetName(filter.getMessage() + " controlled by " + opponent.getLogName());
            ability.addTarget(newTarget);
        }
    }
}
