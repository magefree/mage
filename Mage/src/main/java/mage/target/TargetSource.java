

package mage.target;

import mage.MageObject;
import mage.abilities.Ability;
import mage.cards.Card;
import mage.constants.Zone;
import mage.filter.FilterSource;
import mage.game.Game;
import mage.game.command.CommandObject;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.players.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * 609.7a. If an effect requires a player to choose a source of damage, they may choose a permanent;
 * a spell on the stack (including a permanent spell); any object referred to by an object on the stack,
 * by a replacement or prevention effect that's waiting to apply, or by a delayed triggered ability
 * that's waiting to trigger (even if that object is no longer in the zone it used to be in); or a
 * face-up object in the command zone. A source doesn't need to be capable of dealing damage to be
 * a legal choice. The source is chosen when the effect is created. If the player chooses a permanent,
 * the effect will apply to the next damage dealt by that permanent, regardless of whether it's combat
 * damage or damage dealt as the result of a spell or ability. If the player chooses a permanent spell,
 * the effect will apply to any damage dealt by that spell and any damage dealt by the permanent that
 * spell becomes when it resolves.
 *
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
    public boolean canChoose(UUID sourceControllerId, Ability source, Game game) {
        return canChooseFromPossibleTargets(sourceControllerId, source, game);
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
        for (Card card : game.getExile().getCardsInRange(game, sourceControllerId)) {
            if (filter.match(card, sourceControllerId, source, game)) {
                possibleTargets.add(card.getId());
            }
        }
        for (CommandObject commandObject : game.getState().getCommand()) {
            if (filter.match(commandObject, sourceControllerId, source, game)) {
                possibleTargets.add(commandObject.getId());
            }
        }

        return keepValidPossibleTargets(possibleTargets, sourceControllerId, source, game);
    }

    @Override
    public TargetSource copy() {
        return new TargetSource(this);
    }

}
