

package mage.target;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.cards.Card;
import mage.constants.Zone;
import mage.filter.FilterObject;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TargetSource extends TargetObject {

    protected final FilterObject filter;

    public TargetSource() {
        this(1, 1, new FilterObject("source of your choice"));
    }

    public TargetSource(FilterObject filter) {
        this(1, 1, filter);
    }

    public TargetSource(int numTargets, FilterObject filter) {
        this(numTargets, numTargets, filter);
    }

    public TargetSource(int minNumTargets, int maxNumTargets, FilterObject filter) {
        super(minNumTargets, maxNumTargets, Zone.ALL, true);
        this.filter = filter;
        this.targetName = filter.getMessage();        
    }

    public TargetSource(final TargetSource target) {
        super(target);
        this.filter = target.filter.copy();
    }

    @Override
    public FilterObject getFilter() {
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
            }
            else {
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
    public boolean canChoose(UUID sourceControllerId, Ability source, Game game) {
        return canChoose(sourceControllerId, game);
    }

    @Override
    public boolean canChoose(UUID sourceControllerId, Game game) {
        int count = 0;
        for (StackObject stackObject: game.getStack()) {
            if (game.getState().getPlayersInRange(sourceControllerId, game).contains(stackObject.getControllerId()) && filter.match(stackObject, game)) {
                count++;
                if (count >= this.minNumberOfTargets) {
                    return true;
                }
            }
        }
        for (Permanent permanent: game.getBattlefield().getActivePermanents(sourceControllerId, game)) {
            if (filter.match(permanent, game)) {
                count++;
                if (count >= this.minNumberOfTargets) {
                    return true;
                }
            }
        }
        for (Player player : game.getPlayers().values()) {
            for (Card card : player.getGraveyard().getCards(game)) {
                if (filter.match(card, game)) {
                    count++;
                    if (count >= this.minNumberOfTargets) {
                        return true;
                    }
                }
            }
        }
        for (Card card : game.getExile().getAllCards(game)) {
            if (filter.match(card, game)) {
                count++;
                if (count >= this.minNumberOfTargets) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        return possibleTargets(sourceControllerId, game);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Game game) {
        Set<UUID> possibleTargets = new HashSet<>();
        for (StackObject stackObject: game.getStack()) {
            if (game.getState().getPlayersInRange(sourceControllerId, game).contains(stackObject.getControllerId()) && filter.match(stackObject, game)) {
                possibleTargets.add(stackObject.getId());
            }
        }
        for (Permanent permanent: game.getBattlefield().getActivePermanents(sourceControllerId, game)) {
            if (filter.match(permanent, game)) {
                possibleTargets.add(permanent.getId());
            }
        }
        for (Player player : game.getPlayers().values()) {
            for (Card card : player.getGraveyard().getCards(game)) {
                if (filter.match(card, game)) {
                    possibleTargets.add(card.getId());
                }
            }
        }
        for (Card card : game.getExile().getAllCards(game)) {
            if (filter.match(card, game)) {
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
