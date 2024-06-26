package mage.target.targetadjustment;

import mage.abilities.Ability;
import mage.filter.Filter;
import mage.filter.predicate.card.OwnerIdPredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.targetpointer.FirstTargetPointer;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author notgreat
 */
public class DamagedPlayerControlsTargetAdjuster extends GenericTargetAdjuster {
    private final boolean owner;

    /**
     * Use with {@link mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility} with setTargetPointer enabled,
     * or {@link mage.abilities.common.OneOrMoreDealDamageTriggeredAbility} with "SetTargetPointer.PLAYER" or similar.
     * Adjusts the target to only target something the damaged player controls (or owns with alternative constructor)
     * And then removes the effects' target pointer that the triggered ability set
     */
    public DamagedPlayerControlsTargetAdjuster() {
        this(false);
    }

    public DamagedPlayerControlsTargetAdjuster(boolean owner) {
        this.owner = owner;
    }

    @Override
    public void addDefaultTargets(Ability ability) {
        super.addDefaultTargets(ability);
        CardUtil.AssertNoControllerOwnerPredicates(blueprintTarget);
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        UUID opponentId = ability.getEffects().get(0).getTargetPointer().getFirst(game, ability);
        Player opponent = game.getPlayer(opponentId);
        ability.getTargets().clear();
        ability.getAllEffects().setTargetPointer(new FirstTargetPointer());
        if (opponent == null) {
            return;
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
