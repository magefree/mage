

package mage.target.common;

import mage.abilities.Ability;
import mage.filter.common.FilterPermanentOrPlayerWithCounter;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import java.util.UUID;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.CounterPredicate;

/**
 *
 * @author nantuko
 */
public class TargetPermanentOrPlayerWithCounter extends TargetPermanentOrPlayer {

    protected final FilterPermanentOrPlayerWithCounter targetFilter;

    public TargetPermanentOrPlayerWithCounter() {
        this(1, 1);
    }

    public TargetPermanentOrPlayerWithCounter(int numTargets) {
        this(numTargets, numTargets);
    }

    public TargetPermanentOrPlayerWithCounter(int minNumTargets, int maxNumTargets) {
        this(minNumTargets, maxNumTargets, false);
    }

    public TargetPermanentOrPlayerWithCounter(int minNumTargets, int maxNumTargets, boolean notTarget) {
        super(minNumTargets, maxNumTargets, notTarget);
        this.targetFilter = new FilterPermanentOrPlayerWithCounter();
        this.filterPermanent = new FilterPermanent();
        this.filterPermanent.add(new CounterPredicate(null));
        this.targetName = targetFilter.getMessage();
    }

    public TargetPermanentOrPlayerWithCounter(final TargetPermanentOrPlayerWithCounter target) {
        super(target);
        this.targetFilter = target.targetFilter.copy();
        super.setFilter(this.targetFilter);
    }

    @Override
    public TargetPermanentOrPlayerWithCounter copy() {
        return new TargetPermanentOrPlayerWithCounter(this);
    }

    @Override
    public boolean canTarget(UUID id, Game game) {
        Permanent permanent = game.getPermanent(id);
        if (permanent != null) {
            if (permanent.getCounters(game).isEmpty()) {
                return false;
            }
        }
        Player player = game.getPlayer(id);
        if (player != null) {
            if (player.getCounters().isEmpty()) {
                return false;
            }
        }
        return super.canTarget(id, game);
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        Permanent permanent = game.getPermanent(id);
        if (permanent != null) {
            if (permanent.getCounters(game).isEmpty()) {
                return false;
            }
        }
        Player player = game.getPlayer(id);
        if (player != null) {
            if (player.getCounters().isEmpty()) {
                return false;
            }
        }
        return super.canTarget(id, source, game);
    }

}
