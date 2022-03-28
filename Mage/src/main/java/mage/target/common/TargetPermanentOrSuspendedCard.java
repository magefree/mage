package mage.target.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.cards.Card;
import mage.constants.Zone;
import mage.filter.Filter;
import mage.filter.common.FilterPermanentOrSuspendedCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetImpl;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author emerald000
 */
public class TargetPermanentOrSuspendedCard extends TargetImpl {

    protected final FilterPermanentOrSuspendedCard filter;

    public TargetPermanentOrSuspendedCard() {
        this(new FilterPermanentOrSuspendedCard(), false);
    }

    public TargetPermanentOrSuspendedCard(FilterPermanentOrSuspendedCard filter, boolean notTarget) {
        super(notTarget);
        this.filter = filter;
        this.zone = Zone.ALL;
        this.targetName = filter.getMessage();
        this.minNumberOfTargets = 1;
        this.maxNumberOfTargets = 1;
    }

    public TargetPermanentOrSuspendedCard(final TargetPermanentOrSuspendedCard target) {
        super(target);
        this.filter = target.filter.copy();
    }

    @Override
    public Filter<MageObject> getFilter() {
        return this.filter;
    }

    @Override
    public TargetPermanentOrSuspendedCard copy() {
        return new TargetPermanentOrSuspendedCard(this);
    }

    @Override
    public boolean canChoose(UUID sourceControllerId, Ability source, Game game) {
        MageObject sourceObject = game.getObject(source);
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter.getPermanentFilter(), sourceControllerId, game)) {
            if (permanent.canBeTargetedBy(sourceObject, sourceControllerId, game) && filter.match(permanent, sourceControllerId, source, game)) {
                return true;
            }
        }
        for (Card card : game.getExile().getAllCards(game)) {
            if (filter.match(card, sourceControllerId, source, game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> possibleTargets = new HashSet<>(20);
        MageObject sourceObject = game.getObject(source);
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter.getPermanentFilter(), sourceControllerId, game)) {
            if (permanent.canBeTargetedBy(sourceObject, sourceControllerId, game) && filter.match(permanent, sourceControllerId, source, game)) {
                possibleTargets.add(permanent.getId());
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
    public boolean canTarget(UUID id, Game game) {
        Permanent permanent = game.getPermanent(id);
        if (permanent != null) {
            return filter.match(permanent, game);
        }
        Card card = game.getExile().getCard(id, game);
        return filter.match(card, game);
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        Permanent permanent = game.getPermanent(id);
        if (permanent != null) {
            if (source != null) {
                MageObject targetSource = game.getObject(source);
                return permanent.canBeTargetedBy(targetSource, source.getControllerId(), game)
                        && filter.match(permanent, source.getControllerId(), source, game);
            } else {
                return filter.match(permanent, game);
            }
        }
        Card card = game.getExile().getCard(id, game);
        return filter.match(card, game);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        return this.canTarget(id, source, game);
    }

    @Override
    public boolean canChoose(UUID sourceControllerId, Game game) {
        return this.canChoose(sourceControllerId, null, game);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Game game) {
        return this.possibleTargets(sourceControllerId, null, game);
    }

    @Override
    public String getTargetedName(Game game) {
        StringBuilder sb = new StringBuilder();
        for (UUID targetId : this.getTargets()) {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent != null) {
                sb.append(permanent.getLogName()).append(' ');
            } else {
                Card card = game.getExile().getCard(targetId, game);
                if (card != null) {
                    sb.append(card.getLogName()).append(' ');
                }
            }
        }
        return sb.toString().trim();
    }
}
