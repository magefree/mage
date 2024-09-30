package mage.target.targetadjustment;

import mage.abilities.Ability;
import mage.filter.Filter;
import mage.filter.predicate.card.OwnerIdPredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetCard;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author notgreat
 */
public class ForEachOpponentTargetsAdjuster extends GenericTargetAdjuster {
    private final boolean owner;

    /**
     * Duplicates the permanent target for each opponent.
     * Filtering of permanent's controllers will be handled inside, so
     * do not pass a blueprint target with a controller restriction filter/predicate.
     */
    public ForEachOpponentTargetsAdjuster() {
        this(false);
    }

    public ForEachOpponentTargetsAdjuster(boolean owner) {
        this.owner = owner;
    }

    @Override
    public void addDefaultTargets(Ability ability) {
        super.addDefaultTargets(ability);
        if (blueprintTarget instanceof TargetCard && !owner) {
            throw new IllegalArgumentException("EachOpponentPermanentTargetsAdjuster has TargetCard but checking for Controller instead of Owner - " + blueprintTarget);
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
            if (owner) {
                filter.add(new OwnerIdPredicate(opponentId));
                newTarget.withTargetName(filter.getMessage() + " (owned by " + opponent.getLogName() + ")");
            } else {
                filter.add(new ControllerIdPredicate(opponentId));
                newTarget.withTargetName(filter.getMessage() + " (controlled by " + opponent.getLogName() + ")");
            }
            ability.addTarget(newTarget);
        }
    }
}
