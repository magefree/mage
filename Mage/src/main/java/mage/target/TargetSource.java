

package mage.target;

import mage.MageObject;
import mage.abilities.Ability;
import mage.cards.Card;
import mage.constants.Zone;
import mage.filter.FilterSource;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.players.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class TargetSource extends TargetObject {

    protected final FilterSource filter;

    public TargetSource() {
        this(1, 1, new FilterSource("source of your choice"));
    }

    public TargetSource(FilterSource filter) {
        this(1, 1, filter);
    }

    public TargetSource(int numTargets, FilterSource filter) {
        this(numTargets, numTargets, filter);
    }

    public TargetSource(int minNumTargets, int maxNumTargets, FilterSource filter) {
        super(minNumTargets, maxNumTargets, Zone.ALL, true);
        this.filter = filter;
        this.targetName = filter.getMessage();
    }

    protected TargetSource(final TargetSource target) {
        super(target);
        this.filter = target.filter.copy();
    }

    @Override
    public FilterSource getFilter() {
        return filter;
    }

    @Override
    public void add(UUID id, Game game) {
        addTarget(id, null, game);
    }

    @Override
    public void addTarget(UUID id, Ability source, Game game) {
        if (targets.size() < maxNumberOfTargets) {
            MageObject object = game.getObject(id);
            if (object instanceof StackObject) {
                addTarget(((StackObject) object).getSourceId(), source, game, notTarget);
            } else {
                addTarget(id, source, game, notTarget);
            }
            if (object != null && !game.isSimulation()) {
                game.informPlayers("Selected " + object.getLogName() + " as source");
            }
        }

    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean canChoose(UUID sourceControllerId, Game game) {
        return canChoose(sourceControllerId, (Ability) null, game);
    }

    @Override
    public boolean canChoose(UUID sourceControllerId, Ability source, Game game) {
        int count = 0;
        for (StackObject stackObject : game.getStack()) {
            if (game.getState().getPlayersInRange(sourceControllerId, game).contains(stackObject.getControllerId())
                    && filter.match(stackObject, sourceControllerId, source, game)) {
                count++;
                if (count >= this.minNumberOfTargets) {
                    return true;
                }
            }
        }
        for (Permanent permanent : game.getBattlefield().getActivePermanents(sourceControllerId, game)) {
            if (filter.match(permanent, sourceControllerId, source, game)) {
                count++;
                if (count >= this.minNumberOfTargets) {
                    return true;
                }
            }
        }
        for (Player player : game.getPlayers().values()) {
            for (Card card : player.getGraveyard().getCards(game)) {
                if (filter.match(card, sourceControllerId, source, game)) {
                    count++;
                    if (count >= this.minNumberOfTargets) {
                        return true;
                    }
                }
            }
        }
        for (Card card : game.getExile().getAllCards(game)) {
            if (filter.match(card, sourceControllerId, source, game)) {
                count++;
                if (count >= this.minNumberOfTargets) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Game game) {
        return possibleTargets(sourceControllerId, (Ability) null, game);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> possibleTargets = new HashSet<>();
        for (StackObject stackObject : game.getStack()) {
            if (game.getState().getPlayersInRange(sourceControllerId, game).contains(stackObject.getControllerId())
                    && filter.match(stackObject, sourceControllerId, source, game)) {
                possibleTargets.add(stackObject.getId());
            }
        }
        for (Permanent permanent : game.getBattlefield().getActivePermanents(sourceControllerId, game)) {
            if (filter.match(permanent, sourceControllerId, source, game)) {
                possibleTargets.add(permanent.getId());
            }
        }
        for (Player player : game.getPlayers().values()) {
            for (Card card : player.getGraveyard().getCards(game)) {
                if (filter.match(card, sourceControllerId, source, game)) {
                    possibleTargets.add(card.getId());
                }
            }
        }
        for (Card card : game.getExile().getAllCards(game)) {
            if (filter.match(card, sourceControllerId, source, game)) {
                possibleTargets.add(card.getId());
            }
        }
        return possibleTargets;
    }

    @Override
    public TargetSource copy() {
        return new TargetSource(this);
    }

}
